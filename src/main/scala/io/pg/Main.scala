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
import cats.effect.Blocker

object Main extends IOApp {

  val txt = """
  let Package =
      < Local : { relativePath : Text }
      | GitHub : { repository : Text, revision : Text }
      | Hackage : { package : Text, version : Text }
      >

in  [ Package.GitHub
        { repository =
            "https://github.com/Gabriel439/Haskell-Turtle-Library.git"
        , revision = "ae5edf227b515b34c1cb6c89d9c58ea0eece12d5"
        }
    ]
  """

  val theJson = Blocker[IO]
    .use { blocker =>
      import io.github.vigoo.prox._
      import io.github.vigoo.prox.syntax.catsInterpolation._

      implicit val runner: ProcessRunner[IO] = new JVMProcessRunner

      proc"dhall-to-json"
        .fromStream(fs2.Stream.emit(txt).through(fs2.text.utf8Encode[IO]), flushChunks = true)
        .toFoldMonoid(fs2.text.utf8Decode[IO])
        .run(blocker)
        .map(_.output) //todo error handling
    }
    .flatMap(s => IO(println(s)))

  val logger = StaticLoggerBinder.baseLogger

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
    theJson *>
      AppConfig
        .appConfig
        .resource[IO]
        .flatMap(serve)
        .use(_ => IO.never)

}
