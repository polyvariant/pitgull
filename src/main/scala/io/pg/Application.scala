package io.pg

import cats.syntax.all._
import org.http4s.implicits._
import org.http4s.HttpApp
import cats.effect.Resource
import cats.effect.ContextShift
import io.pg.webhook._
import cats.data.NonEmptyList
import sttp.tapir.server.ServerEndpoint
import fs2.concurrent.Queue
import Prelude._
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.messaging._
import io.pg.background.BackgroundProcess
import io.odin.Logger
import io.pg.config.ProjectConfigReader
import io.pg.gitlab.Gitlab
import cats.effect.Blocker
import sttp.client.http4s.Http4sBackend
import cats.effect.ConcurrentEffect
import org.http4s.client.blaze.BlazeClientBuilder
import scala.concurrent.ExecutionContext
import sttp.client.SttpBackend

sealed trait Event extends Product with Serializable

object Event {
  final case class Webhook(value: WebhookEvent) extends Event
  final case class ProjectAction(value: io.pg.ProjectAction) extends Event
}

final class Application[F[_]](
  val routes: HttpApp[F],
  val background: NonEmptyList[BackgroundProcess[F]]
)

object Application {

  def resource[F[_]: ConcurrentEffect: ContextShift: Logger](
    config: AppConfig
  ): Resource[F, Application[F]] = {
    implicit val projectConfigReader = ProjectConfigReader.test[F]

    Blocker[F].flatMap { blocker =>
      Queue
        .bounded[F, Event](config.queues.maxSize)
        .map(Channel.fromQueue)
        .resource
        .flatMap { eventChannel =>
          val webhookChannel =
            eventChannel.only[Event.Webhook].imap(_.value)(Event.Webhook)

          val projectActionChannel =
            eventChannel.only[Event.ProjectAction].imap(_.value)(Event.ProjectAction)

          BlazeClientBuilder[F](ExecutionContext.global)
            .resource
            .map(
              org
                .http4s
                .client
                .middleware
                .Logger(logHeaders = true, logBody = false)
            )
            .map { client =>
              implicit val backend: SttpBackend[F, Nothing, Nothing] =
                Http4sBackend.usingClient[F](client, blocker)

              implicit val gitlab: Gitlab[F] =
                Gitlab.sttpInstance[F](config.git.apiUrl, config.git.apiToken)
              implicit val projectActions: ProjectActions[F] =
                ProjectActions.instance[F]
              implicit val stateResolver: StateResolver[F] =
                StateResolver.instance[F]

              val webhookProcess = BackgroundProcess.fromProcessor(
                webhookChannel
              )(WebhookProcessor.instance[F](projectActionChannel))

              val projectActionProcess = BackgroundProcess.fromProcessor(
                projectActionChannel
              )(ProjectActions.processor[F])

              import sttp.tapir.server.http4s._

              val endpoints: NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] =
                NonEmptyList.of(WebhookRouter.routes[F](webhookChannel)).flatten

              new Application[F](
                routes = endpoints.toList.toRoutes.orNotFound,
                background = NonEmptyList.of(webhookProcess, projectActionProcess)
              )
            }
        }
    }
  }

}
