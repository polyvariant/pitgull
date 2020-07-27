package io.pg

import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO

import org.http4s.server.blaze.BlazeServerBuilder
import scala.concurrent.ExecutionContext
import io.odin.formatter.Formatter
import io.pg.Prelude._
import cats.implicits._
import org.http4s.client.blaze.BlazeClientBuilder
import cats.effect.Blocker
import sttp.model.Uri
import sttp.client.http4s.Http4sBackend
import io.pg.gitlab.Gitlab

object Main extends IOApp {

  import io.odin._

  val logger = consoleLogger[IO](formatter = Formatter.colorful)

  val serve = Application
    .resource[IO]
    .flatMap { resources =>
      // val server = BlazeServerBuilder[IO](ExecutionContext.global)
      //   .withHttpApp(resources.routes)
      //   .bindHttp(port = resources.config.http.port, host = "0.0.0.0")
      //   .withBanner(resources.config.meta.banner.linesIterator.toList)
      //   .resource

      // val logStarted = logger
      //   .info(
      //     "Started application",
      //     Map("version" -> resources.config.meta.version, "scalaVersion" -> resources.config.meta.scalaVersion)
      //   )

      // server *> logStarted.resource_
      Blocker[IO].flatMap(Http4sBackend.usingDefaultClientBuilder[IO](_)).evalTap(_ => IO(println("built app"))).evalMap {
        implicit backend =>
          import sttp.client.UriContext
          val gitlab = Gitlab.sttpInstance[IO](uri"${resources.config.git.apiUrl}", resources.config.git.apiToken)

          gitlab.acceptMergeRequest(20190338, 1)
      }

    }

  def run(args: List[String]): IO[ExitCode] =
    serve
      .use(_ => IO.pure(ExitCode.Success))

  // .use(_ => IO.never)

}
