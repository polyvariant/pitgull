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

trait ProjectConfigReader[F[_]] {
  def readConfig: F[ProjectConfig]
}

object ProjectConfigReader extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    Blocker[IO]
      .use { blocker =>
        dhallJsonStringConfig[IO](blocker).readConfig.flatMap(pc => IO(println(pc)))
      }
      .as(ExitCode.Success)

  def dhallJsonStringConfig[F[_]: Concurrent: ContextShift](blocker: Blocker): ProjectConfigReader[F] =
    new ProjectConfigReader[F] {
      val filePath = "./example.dhall"
      val input = fs2.io.file.readAll[F](Paths.get(filePath), blocker, 4096)

      import io.github.vigoo.prox._
      implicit val runner: ProcessRunner[F] = new JVMProcessRunner

      val readConfig: F[ProjectConfig] =
        Process[F]("dhall-to-json")
          .`with`("TOKEN" -> "demo-token")
          .fromStream(input, flushChunks = true)
          .toFoldMonoid(fs2.text.utf8Decode[F])
          .run(blocker)
          .ensure(new Throwable("Invalid exit code of dhall-to-json"))(_.exitCode == ExitCode.Success)
          .map(_.output) //todo error handling
          .flatMap(io.circe.parser.decode[ProjectConfig](_).liftTo[F])

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
