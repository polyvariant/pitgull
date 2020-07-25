package io.pg

import cats.implicits._
import org.http4s.implicits._
import org.http4s.HttpApp
import cats.effect.Resource
import cats.effect.Concurrent
import cats.effect.ContextShift
import org.http4s.HttpRoutes
import io.pg.Prelude._

final class Application[F[_]](val config: AppConfig, val routes: HttpApp[F])

object Application {

  def resource[F[_]: Concurrent: ContextShift]: Resource[F, Application[F]] =
    AppConfig.appConfig.load[F].resource.map { config =>
      new Application[F](config = config, routes = HttpRoutes.empty[F].orNotFound)
    }

}
