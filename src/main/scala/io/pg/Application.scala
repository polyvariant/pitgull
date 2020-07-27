package io.pg

import cats.implicits._
import org.http4s.implicits._
import org.http4s.HttpApp
import cats.effect.Resource
import cats.effect.Concurrent
import cats.effect.ContextShift
import io.pg.hello.HelloService
import io.pg.hello.HelloRouter
import cats.data.NonEmptyList
import sttp.tapir.server.ServerEndpoint

final class Application[F[_]](val config: AppConfig, val routes: HttpApp[F])

object Application {

  def resource[F[_]: Concurrent: ContextShift]: Resource[F, Application[F]] =
    AppConfig.appConfig.resource[F].map { config =>
      implicit val helloService: HelloService[F] = HelloService.instance

      val routes: NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] = NonEmptyList.of(HelloRouter.routes[F]).flatten

      import sttp.tapir.server.http4s._

      new Application[F](config = config, routes = routes.toList.toRoutes.orNotFound)
    }

}
