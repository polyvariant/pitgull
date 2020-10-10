package io.pg.webhook

import cats.data.NonEmptyList
import sttp.tapir.server.ServerEndpoint
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.messaging.Publisher
import io.pg.messaging.Processor
import io.odin.Logger
import cats.Applicative
import io.pg.config.ProjectConfigReader
import cats.syntax.all._
import cats.MonadError
import io.pg.StateResolver
import io.pg.ProjectActions
import io.pg.ProjectAction
import io.pg.MergeRequestState
import io.pg.config.ProjectConfig
import fs2.Pipe
import io.pg.ProjectActions.Mismatch

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
  ): Processor[F, WebhookEvent] =
    Processor.simple { ev =>
      for {
        _       <- Logger[F].info("Received event", Map("event" -> ev.toString()))
        config  <- ProjectConfigReader[F].readConfig
        state   <- StateResolver[F].resolve(ev)
        actions <- state
                     .traverse(validActions[F](_, config))
                     .map(_.sequence.flattenOption)
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
    state: MergeRequestState,
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
      .emit(state)
      .flatMap(ProjectActions.compile(_, config).pipe(fs2.Stream.emits(_)))
      .through(tapLeftAndDrop(logMismatches))
      .compile
      .toList
  }

}
