package io.pg

import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO

import org.http4s.server.blaze.BlazeServerBuilder
import scala.concurrent.ExecutionContext
import io.pg.Prelude._
import cats.implicits._
import org.http4s.server.middleware
import org.slf4j.impl.StaticLoggerBinder
import scala.util.Try
import io.pg.gitlab.transport.WebhookEvent

object Main extends IOApp {
  println(System.getProperty("java.vendor"))
  println(System.getProperty("java.version"))
  val logger = StaticLoggerBinder.baseLogger

  //loading eagerly
  println(WebhookEvent.codec)

  def serve(config: AppConfig) =
    Application
      .resource[IO](config)
      .flatMap { resources =>
        val server = BlazeServerBuilder[IO](ExecutionContext.global)
          .withHttpApp(
            middleware.Logger.httpApp(logHeaders = true, logBody = true, logAction = (logger.debug(_: String)).some)(resources.routes)
          )
          .bindHttp(port = config.http.port, host = "0.0.0.0")
          .withBanner(config.meta.banner.linesIterator.toList)
          .resource

        val logStarted = logger
          .info(
            "Started application",
            Map("version" -> config.meta.version, "scalaVersion" -> config.meta.scalaVersion)
          )

        server *> logStarted.resource_
      }

  def run(args: List[String]): IO[ExitCode] =
    AppConfig
      .appConfig
      .resource[IO]
      .flatMap(serve)
      .use(_ => IO.never)

}