package io.pg

import cats.implicits._
import org.http4s.implicits._
import org.http4s.HttpApp
import cats.effect.Resource
import cats.effect.Concurrent
import cats.effect.ContextShift
import org.http4s.HttpRoutes

final class AppResources[F[_]](val config: AppConfig, val routes: HttpApp[F])

object AppResources {

  def resource[F[_]: Concurrent: ContextShift]: Resource[F, AppResources[F]] =
    Resource.liftF(AppConfig.appConfig.load[F]).map { config =>
      new AppResources[F](config = config, routes = HttpRoutes.empty[F].orNotFound)
    }

}
