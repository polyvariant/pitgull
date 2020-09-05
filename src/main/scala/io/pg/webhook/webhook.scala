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

object WebhookRouter {

  object endpoints {
    import sttp.tapir._
    import sttp.tapir.json.circe._

    val webhook = infallibleEndpoint.post.in("webhook").in(jsonBody[WebhookEvent])
  }

  def routes[F[_]: Applicative](eventPublisher: Publisher[F, WebhookEvent]): NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] =
    NonEmptyList.of(
      endpoints.webhook.serverLogicRecoverErrors(eventPublisher.publish)
    )

}

object WebhookProcessor {

  def instance[F[_]: ProjectConfigReader: ProjectActions: StateResolver: Logger: MonadError[*[_], Throwable]]: Processor[F, WebhookEvent] =
    Processor.simple { ev =>
      for {
        _      <- Logger[F].info("Received event", Map("event" -> ev.toString()))
        config <- ProjectConfigReader[F].readConfig
        state  <- StateResolver[F].resolve(ev)
        actions = state.traverse(ProjectActions.compile(_, config)).flattenOption
        _      <- Logger[F].debug("All actions to execute", Map("actions" -> actions.toString))
        _      <- actions.traverse_ { action =>
                    Logger[F].info("About to execute action", Map("action" -> action.toString)) *>
                      ProjectActions[F].execute(action)
                  }
      } yield ()
    }

}
