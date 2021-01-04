package io.pg.webhook

import cats.Applicative
import cats.MonadError
import cats.data.NonEmptyList
import cats.syntax.all._
import fs2.Pipe
import io.odin.Logger
import io.pg.MergeRequestState
import io.pg.ProjectAction
import io.pg.ProjectActions
import io.pg.ProjectActions.Mismatch
import io.pg.StateResolver
import io.pg.config.ProjectConfig
import io.pg.config.ProjectConfigReader
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.messaging.Publisher
import sttp.tapir.server.ServerEndpoint

object WebhookRouter {

  object endpoints {
    import sttp.tapir._
    import sttp.tapir.json.circe._

    val webhook =
      infallibleEndpoint.post.in("webhook").in(jsonBody[WebhookEvent])
  }

  def routes[F[_]: Applicative](
    eventPublisher: Publisher[F, WebhookEvent]
  ): NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] =
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
    for {
      _       <- Logger[F].info("Received event", Map("event" -> ev.toString()))
      config  <- ProjectConfigReader[F].readConfig(ev.project)
      states  <- StateResolver[F].resolve(ev.project)
      actions <- validActions[F](states, config)
      _       <- Logger[F].debug(
                   "All actions to execute",
                   Map("actions" -> actions.toString)
                 )
      _       <- actions.traverse_ { action =>
                   Logger[F].info(
                     "About to execute action",
                     Map("action" -> action.toString)
                   ) *>
                     ProjectActions[F].execute(action)
                 }
    } yield ()
  }

  private def validActions[F[_]: Logger: Applicative](
    states: List[MergeRequestState],
    config: ProjectConfig
  )(
    implicit SC: fs2.Stream.Compiler[F, F]
  ): F[List[ProjectAction]] = {
    def tapLeftAndDrop[L, R](log: L => F[Unit]): Pipe[F, Either[L, R], R] =
      _.evalTap(_.leftTraverse(log)).map(_.toOption).unNone

    val logMismatches: NonEmptyList[Mismatch] => F[Unit] = e =>
      Logger[F].debug(
        "Ignoring action because it didn't match rules",
        Map("rules" -> e.map(_.toString).mkString_(", "))
      )

    fs2
      .Stream
      .emits(states)
      .flatMap(ProjectActions.compile(_, config).pipe(fs2.Stream.emits(_)))
      .through(tapLeftAndDrop(logMismatches))
      .compile
      .toList
  }

}
