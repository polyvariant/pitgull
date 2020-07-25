package io.pg

import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO

import org.http4s.server.blaze.BlazeServerBuilder
import scala.concurrent.ExecutionContext

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    AppResources
      .resource[IO]
      .flatMap { resources =>
        BlazeServerBuilder[IO](ExecutionContext.global)
          .withHttpApp(resources.routes)
          .bindHttp(port = resources.config.http.port, host = "0.0.0.0")
          .withBanner(resources.config.meta.banner.linesIterator.toList)
          .resource
      }
      .use(_ => IO.never)

}
