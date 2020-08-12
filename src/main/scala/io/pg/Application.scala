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

final class Application[F[_]](val routes: HttpApp[F])

object Application {

  def resource[F[_]: Concurrent: ContextShift](config: AppConfig): Resource[F, Application[F]] = {
    val routes: NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] = NonEmptyList.of(WebhookRouter.routes[F]).flatten

    import sttp.tapir.server.http4s._

    new Application[F](routes = routes.toList.toRoutes.orNotFound).pure[Resource[F, *]]
  }

}
