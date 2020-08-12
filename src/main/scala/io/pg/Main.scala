package io.pg

import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO

import org.http4s.server.blaze.BlazeServerBuilder
import scala.concurrent.ExecutionContext
import io.odin.formatter.Formatter
import io.pg.Prelude._
import cats.implicits._

object Main extends IOApp {

  import io.odin._

  val logger = consoleLogger[IO](formatter = Formatter.colorful)

  val serve = Application
    .resource[IO]
    .flatMap { resources =>
      val server = BlazeServerBuilder[IO](ExecutionContext.global)
        .withHttpApp(resources.routes)
        .bindHttp(port = resources.config.http.port, host = "0.0.0.0")
        .withBanner(resources.config.meta.banner.linesIterator.toList)
        .resource

      val logStarted = logger
        .info(
          "Started application",
          Map("version" -> resources.config.meta.version, "scalaVersion" -> resources.config.meta.scalaVersion)
        )

      server *> logStarted.resource_
    }

  def run(args: List[String]): IO[ExitCode] =
    serve
      .use(_ => IO.never)

}
