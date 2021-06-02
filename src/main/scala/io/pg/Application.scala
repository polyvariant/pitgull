package io.pg

import cats.data.NonEmptyList
import cats.effect.ConcurrentEffect
import cats.effect.Resource
import cats.syntax.all._
import fs2.concurrent.Queue
import io.odin.Logger
import io.pg.background.BackgroundProcess
import io.pg.config.ProjectConfigReader
import io.pg.gitlab.Gitlab
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.messaging._
import io.pg.webhook._
import org.http4s.HttpApp
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import sttp.capabilities.fs2.Fs2Streams
import sttp.client3.SttpBackend
import sttp.client3.http4s.Http4sBackend

import scala.concurrent.ExecutionContext

import Prelude._

sealed trait Event extends Product with Serializable

object Event {
  final case class Webhook(value: WebhookEvent) extends Event
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

    Resource.unit[F].flatMap { blocker =>
      Queue
        .bounded[F, Event](config.queues.maxSize)
        .map(Channel.fromQueue)
        .resource
        .flatMap { eventChannel =>
          implicit val webhookChannel: Channel[F, WebhookEvent] =
            eventChannel.only[Event.Webhook].imap(_.value)(Event.Webhook)

          BlazeClientBuilder[F](ExecutionContext.global)
            .resource
            .map(
              org
                .http4s
                .client
                .middleware
                .Logger(logHeaders = true, logBody = false, redactHeadersWhen = config.middleware.sensitiveHeaders.contains)
            )
            .map { client =>
              implicit val backend: SttpBackend[F, Fs2Streams[F]] =
                Http4sBackend.usingClient[F](client, blocker)

              implicit val gitlab: Gitlab[F] =
                Gitlab.sttpInstance[F](config.git.apiUrl, config.git.apiToken)

              implicit val projectActions: ProjectActions[F] =
                ProjectActions.instance[F]

              implicit val stateResolver: StateResolver[F] =
                StateResolver.instance[F]

              implicit val mergeRequests: MergeRequests[F] =
                MergeRequests.instance[F]

              val webhookProcess = BackgroundProcess.fromProcessor(
                webhookChannel
              )(Processor.simple(WebhookProcessor.instance[F]))

              new Application[F](
                routes = WebhookRouter.routes[F].orNotFound,
                background = NonEmptyList.one(webhookProcess)
              )
            }
        }
    }
  }

}
