package io.pg

import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO

import org.http4s.server.blaze.BlazeServerBuilder
import scala.concurrent.ExecutionContext
import io.odin.formatter.Formatter

object Main extends IOApp {

  import io.odin._

  val logger = consoleLogger[IO](formatter = Formatter.colorful)

  def run(args: List[String]): IO[ExitCode] =
    AppResources
      .resource[IO]
      .flatMap { resources =>
        BlazeServerBuilder[IO](ExecutionContext.global)
          .withHttpApp(resources.routes)
          .bindHttp(port = resources.config.http.port, host = "0.0.0.0")
          .withBanner(resources.config.meta.banner.linesIterator.toList)
          .resource
          .evalTap(_ =>
            logger
              .info(
                "Started application",
                Map("version" -> resources.config.meta.version, "scalaVersion" -> resources.config.meta.scalaVersion)
              )
          )
      }
      .use(_ => IO.never)

}
