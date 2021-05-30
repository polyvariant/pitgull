package io.pg.config

import cats.Applicative
import cats.MonadThrow
import cats.effect.ExitCode
import cats.syntax.all._
import java.nio.file.Paths
import scala.util.chaining._
import cats.Applicative
import cats.tagless.finalAlg
import io.github.vigoo.prox.ProxFS2
import io.pg.gitlab.webhook.Project
import cats.effect.Sync
import io.github.vigoo.prox.ProxFS2
import io.pg.MergeRequestState
import io.pg.ProjectActions
import io.pg.gitlab.Gitlab
import io.pg.gitlab.Gitlab.MergeRequestInfo.Status.Success
import io.pg.gitlab.Gitlab.MergeRequestInfo.Status.Other
import io.circe.Decoder
import io.circe.generic.extras.ConfiguredJsonCodec
import io.circe.Codec
import cats.data.NonEmptyList
import io.pg.config.Mismatch.Author
import io.pg.config.Mismatch.Description
import io.pg.config.Mismatch.NoneMatched
import io.circe.generic.extras.Configuration
import io.pg.nix.Nix

import java.nio.file.Paths
import scala.util.chaining._

@finalAlg
trait ProjectConfigReader[F[_]] {
  //ideally this will have F inside, or something
  //todo rename
  def readConfig(project: Project): MergeRequestState => F[ProjectActions.Matched[Unit]]
}

object ProjectConfigReader {

  def test[F[_]: Applicative]: ProjectConfigReader[F] =
    new ProjectConfigReader[F] {

      def semver(level: String) = Matcher.Description(TextMatcher.Matches(s"(?s).*labels:.*semver-$level.*"))

      val anyLibraryPatch = semver("patch")

      val fromWMS = Matcher.Description(TextMatcher.Matches("""(?s).*((com\.ocado\.ospnow\.wms)|(com\.ocado\.gm\.wms))(?s).*"""))

      val config = Matcher.Many(
        List(
          Matcher.Author(TextMatcher.Matches("(scala_steward)|(michal.pawlik)|(j.kozlowski)")),
          Matcher.PipelineStatus("SUCCESS"),
          (
            semver("minor").and(fromWMS)
          ).or(semver("patch"))
        )
      )

      def readConfig(project: Project): MergeRequestState => F[ProjectActions.Matched[Unit]] =
        s => ProjectActions.compileMatcher(config).matches(s).pure[F]

    }

  def nixJsonConfig[F[_]: Concurrent]: F[ProjectConfigReader[F]] = {
    val inputState = """{ status = "success"; author = "user1@gmail.com"; description = "hello werld"; }"""

    import Nix.syntax._

    implicit val statusToNix: Nix.From[Gitlab.MergeRequestInfo.Status] = Nix.From[String].contramap {
      case Success      => "success"
      case Other(value) => value
    }

    implicit val stateAsNix: Nix.From[MergeRequestState] = mrs =>
      Nix.Record(
        mrs.status.toNix.at("status") ++
          mrs.authorUsername.toNix.at("author") ++
          mrs.description.getOrElse("").toNix.at("description")
      )

    def args(state: MergeRequestState) = {
      println(state.toNix.render)
      List("eval", s"(import /dev/stdin) ${state.toNix.render}", "--json")
    }
    val prox: ProxFS2[F] = ProxFS2[F](blocker)
    import prox.ProcessRunner
    import prox.JVMProcessInfo
    import prox.JVMProcessRunner
    import prox.Process
    import prox.ProcessResult

    def checkExitCode[O, E]: F[ProcessResult[O, E]] => F[ProcessResult[O, E]] =
      _.ensure(new Throwable("Invalid exit code"))(
        _.exitCode == ExitCode.Success
      )

    implicit val runner: ProcessRunner[JVMProcessInfo] = new JVMProcessRunner

    val instance: ProjectConfigReader[F] = new ProjectConfigReader[F] {
      import Result._

      implicit val decoder: Decoder[ProjectActions.Matched[Unit]] = Decoder[Result].map {
        case Ok                => Right(())
        case NotOk(mismatches) =>
          def convertMismatch(m: Mismatch): ProjectActions.Mismatch = m match {
            case Mismatch.Status(expected, actual) => ProjectActions.Mismatch.ValueMismatch(expected.value, actual.value).atPath("status")
            case Author(expected, actual)          =>
              expected match {
                case TextRule.Equal(v)   => ProjectActions.Mismatch.ValueMismatch(v, actual).atPath("author")
                case TextRule.Matches(r) =>
                  ProjectActions.Mismatch.RegexMismatch(r, actual).atPath("author")
              }
            case Description(expected, actual)     =>
              //copy-paste from author
              expected match {
                case TextRule.Equal(v)   => ProjectActions.Mismatch.ValueMismatch(v, actual).atPath("description")
                case TextRule.Matches(r) =>
                  ProjectActions.Mismatch.RegexMismatch(r, actual).atPath("description")
              }
            case NoneMatched(expected)             =>
              //todo: unnecessary wrapping in List in this model
              ProjectActions.Mismatch.ManyFailed(List(expected.map(convertMismatch)))
          }

          Left(mismatches.map(convertMismatch))
      }

      def readConfig(project: Project): MergeRequestState => F[ProjectActions.Matched[Unit]] = state =>
        Process("nix", args(state))
          .fromFile(Paths.get("./wms.nix"))
          .toFoldMonoid(fs2.text.utf8Decode[F])
          .run()
          .pipe(checkExitCode)
          .map(_.output)
          .flatTap(out => Sync[F].delay(println(out)))
          .flatMap(io.circe.parser.decode[ProjectConfig](_).liftTo[F])
    }

    val ensureCommandExists =
      Process("bash", "-c" :: s"command -v nix" :: Nil)
        .drainOutput(_.drain)
        .run()
        .pipe(checkExitCode)
        .adaptError { case e =>
          new Throwable(s"Command nix not found", e)
        }

    ensureCommandExists.as(instance)
  }

}

object circe {
  implicit val circeConfig: Configuration =
    Configuration.default.withDiscriminator("kind").withSnakeCaseConstructorNames
}

import circe.circeConfig

@ConfiguredJsonCodec()
sealed trait Result extends Product with Serializable

object Result {
  case object Ok extends Result

  final case class NotOk(mismatches: NonEmptyList[Mismatch]) extends Result
}

@ConfiguredJsonCodec()
sealed trait Mismatch extends Product with Serializable

object Mismatch {
  final case class Status(expected: io.pg.config.Status, actual: io.pg.config.Status) extends Mismatch
  final case class Author(expected: TextRule, actual: String) extends Mismatch
  final case class Description(expected: TextRule, actual: String) extends Mismatch
  final case class NoneMatched(mismatches: NonEmptyList[Mismatch]) extends Mismatch
}

@ConfiguredJsonCodec()
sealed trait TextRule extends Product with Serializable

object TextRule {
  final case class Equal(expected: String) extends TextRule
  final case class Matches(pattern: String) extends TextRule

}

final case class Status(value: String)

object Status {
  implicit val codec: Codec[Status] = io.circe.generic.extras.semiauto.deriveUnwrappedCodec

}
