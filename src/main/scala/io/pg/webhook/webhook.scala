package io.pg.webhook

import cats.Applicative
import cats.MonadError
import cats.Show
import cats.data.EitherNel
import cats.data.NonEmptyList
import cats.implicits._
import fs2.Pipe
import io.odin.Logger
import io.pg.MergeRequestState
import io.pg.MergeRequestState.Mergeability.CanMerge
import io.pg.MergeRequestState.Mergeability.HasConflicts
import io.pg.MergeRequestState.Mergeability.NeedsRebase
import io.pg.ProjectAction
import io.pg.ProjectActions
import io.pg.ProjectActions.Mismatch
import io.pg.StateResolver
import io.pg.config.ProjectConfigReader
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.messaging.Publisher
import sttp.tapir.server.ServerEndpoint

object WebhookRouter {

  object endpoints {
    import sttp.tapir._
    import sttp.tapir.json.circe._
    import sttp.tapir.generic.auto._

    val webhook =
      infallibleEndpoint.post.in("webhook").in(jsonBody[WebhookEvent])
  }

  def routes[F[_]: Applicative](
    eventPublisher: Publisher[F, WebhookEvent]
  ): NonEmptyList[ServerEndpoint[_, _, _, Any, F]] =
    NonEmptyList.of(
      endpoints.webhook.serverLogicRecoverErrors(eventPublisher.publish)
    )

}

object WebhookProcessor {
  import scala.util.chaining._

  def instance[
    F[
      _
    ]: ProjectConfigReader: ProjectActions: StateResolver: Logger: MonadError[*[
      _
    ], Throwable]
  ](
    implicit SC: fs2.Stream.Compiler[F, F]
  ): WebhookEvent => F[Unit] = { ev =>
    val loop: F[Unit] = for {
      config <- ProjectConfigReader[F].readConfig(ev.project)
      states <- StateResolver[F]
                  .resolve(ev.project)
                  .flatMap(validActions[F, Mismatch, MergeRequestState, MergeRequestState](ProjectActions.compile(_, config)))

      nextMR = states.minByOption(_.mergeability)
      nextAction <- nextMR
                      .flatTraverse { mr =>
                        val nextAction = mr.mergeability match {
                          case CanMerge =>
                            ProjectAction.Merge(projectId = mr.projectId, mergeRequestIid = mr.mergeRequestIid).asRight

                          case NeedsRebase =>
                            ProjectAction.Rebase(projectId = mr.projectId, mergeRequestIid = mr.mergeRequestIid).asRight

                          case HasConflicts =>
                            Logger[F]
                              .info(
                                "MR has conflicts, skipping",
                                Map("projectId" -> mr.projectId.show, "mergeRequestIid" -> mr.mergeRequestIid.show)
                              )
                              .asLeft
                        }

                        nextAction.leftSequence.map(_.toOption)
                      }
      _          <- nextAction.traverse_(ProjectActions[F].execute)
    } yield ()

    Logger[F].info("Received event", Map("event" -> ev.toString())) *>
      loop
  }

  private def validActions[F[_]: Logger: Applicative, E: Show, A, B](
    compile: A => List[EitherNel[E, B]]
  )(
    states: List[A]
  )(
    implicit SC: fs2.Stream.Compiler[F, F]
  ): F[List[B]] = {
    def tapLeftAndDrop[L, R](log: L => F[Unit]): Pipe[F, Either[L, R], R] =
      _.evalTap(_.leftTraverse(log)).map(_.toOption).unNone

    val logMismatches: NonEmptyList[E] => F[Unit] = e =>
      Logger[F].debug(
        "Ignoring action because it didn't match rules",
        Map("rules" -> e.map(_.toString).mkString_(", "))
      )

    fs2
      .Stream
      .emits(states)
      .flatMap(compile(_).pipe(fs2.Stream.emits(_)))
      .through(tapLeftAndDrop(logMismatches))
      .compile
      .toList
  }

}
