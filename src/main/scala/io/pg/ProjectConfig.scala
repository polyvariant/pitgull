package io.pg

import cats.effect.Blocker
import cats.effect.Concurrent
import cats.effect.ContextShift
import cats.implicits._
import io.circe.generic.JsonCodec
import java.nio.file.Paths
import io.circe.Decoder
import cats.data.NonEmptyList
import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO
import io.github.vigoo.prox._
import scala.util.chaining._

trait ProjectConfigReader[F[_]] {
  def readConfig: F[ProjectConfig]
}

object ProjectConfigReader extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    Blocker[IO]
      .use { blocker =>
        dhallJsonStringConfig[IO](blocker).flatMap(_.readConfig)
      }
      .flatMap(pc => IO(println(pc)))
      .as(ExitCode.Success)

  def dhallJsonStringConfig[F[_]: Concurrent: ContextShift](blocker: Blocker): F[ProjectConfigReader[F]] = {
    val dhallCommand = "dhall-to-json"
    val filePath = "./example.dhall"

    def checkExitCode[O, E]: F[ProcessResult[O, E]] => F[ProcessResult[O, E]] =
      _.ensure(new Throwable("Invalid exit code"))(_.exitCode == ExitCode.Success)

    implicit val runner: ProcessRunner[F] = new JVMProcessRunner

    val instance: ProjectConfigReader[F] = new ProjectConfigReader[F] {

      val readConfig: F[ProjectConfig] = {
        val input = fs2.io.file.readAll[F](Paths.get(filePath), blocker, 4096)

        Process[F](dhallCommand)
          .`with`("TOKEN" -> "demo-token")
          .fromStream(input, flushChunks = true)
          .toFoldMonoid(fs2.text.utf8Decode[F])
          .run(blocker)
          .pipe(checkExitCode)
          .map(_.output)
          .flatMap(io.circe.parser.decode[ProjectConfig](_).liftTo[F])
      }
    }

    val ensureCommandExists =
      //todo: "command -v" was supposed to be portable
      Process[F](dhallCommand, "--version" :: Nil).drainOutput(_.drain).run(blocker).pipe(checkExitCode).adaptError {
        case e => new Throwable(s"Command $dhallCommand not found", e)
      }

    ensureCommandExists.as(instance)
  }

}

import io.circe.generic.semiauto._

//foo
sealed trait TextMatcher extends Product with Serializable

object TextMatcher {
  final case class Equals(value: String) extends TextMatcher
  final case class Matches(regex: String) extends TextMatcher

  implicit val decoder: Decoder[TextMatcher] = NonEmptyList
    .of[Decoder[TextMatcher]](
      deriveDecoder[Equals].widen,
      deriveDecoder[Matches].widen
    )
    .reduceK

}

sealed trait Match extends Product with Serializable

object Match {
  final case class Author(email: TextMatcher) extends Match
  final case class Description(text: TextMatcher) extends Match
  final case class PipelineStatus(status: String) extends Match

  implicit val decoder: Decoder[Match] = NonEmptyList
    .of[Decoder[Match]](
      deriveDecoder[Author].widen,
      deriveDecoder[Description].widen,
      deriveDecoder[PipelineStatus].widen
    )
    .reduceK

}

@JsonCodec(decodeOnly = true)
final case class Rule(name: String, matches: List[Match])

@JsonCodec(decodeOnly = true)
final case class ProjectConfig(rules: List[Rule])
