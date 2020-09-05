package io.pg

import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO

import org.http4s.server.blaze.BlazeServerBuilder
import scala.concurrent.ExecutionContext
import io.pg.Prelude._
import cats.syntax.all._
import org.http4s.server.middleware
import org.slf4j.impl.StaticLoggerBinder
import cats.effect.Blocker
import io.pg.config.ProjectConfigReader

object Main extends IOApp {

  implicit val logger = StaticLoggerBinder.baseLogger

  def serve(config: AppConfig) =
    Application
      .resource[IO](config)
      .flatMap { resources =>
        val server = BlazeServerBuilder[IO](ExecutionContext.global)
          .withHttpApp(
            middleware
              .Logger
              .httpApp(logHeaders = true, logBody = true, logAction = (logger.debug(_: String)).some)(
                resources.routes
              )
          )
          .bindHttp(port = config.http.port, host = "0.0.0.0")
          .withBanner(config.meta.banner.linesIterator.toList)
          .resource

        val logStarted = logger
          .info(
            "Started application",
            Map("version" -> config.meta.version, "scalaVersion" -> config.meta.scalaVersion)
          )

        server *>
          logStarted.resource_.as(resources.background)
      }

  def run(args: List[String]): IO[ExitCode] =
    Blocker[IO]
      .flatMap { b =>
        ProjectConfigReader.dhallJsonStringConfig[IO](b).flatTap(_.readConfig.flatMap(a => logger.info(a.toString))).resource *>
          AppConfig
            .appConfig
            .resource[IO]
            .flatMap(serve)
      }
      .use(_.parTraverse_(_.run) *> IO.never)

}
