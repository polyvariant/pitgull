package io.pg

import cats.implicits._
import org.http4s.implicits._
import org.http4s.HttpApp
import cats.effect.Resource
import cats.effect.Concurrent
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

sealed trait Event extends Product with Serializable

object Event {
  final case class Webhook(value: WebhookEvent) extends Event
}

final class Application[F[_]](val routes: HttpApp[F], val background: NonEmptyList[BackgroundProcess[F]])

object Application {

  def resource[F[_]: Concurrent: ContextShift: Logger](config: AppConfig): Resource[F, Application[F]] = {

    import sttp.tapir.server.http4s._

    Queue
      .bounded[F, Event](config.queues.maxSize)
      .resource
      .map(Channel.fromQueue)
      .map { eventChannel =>
        val webhookChannel = eventChannel.only[Event.Webhook].imap(_.value)(Event.Webhook)

        val routes: NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] =
          NonEmptyList.of(WebhookRouter.routes[F](webhookChannel)).flatten

        new Application[F](
          routes = routes.toList.toRoutes.orNotFound,
          background = NonEmptyList.one(BackgroundProcess.fromProcessor(webhookChannel)(WebhookProcessor.instance[F]))
        )
      }
  }

}
