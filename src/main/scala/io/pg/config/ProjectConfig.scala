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

      def semver(level: String) = Matcher.Description(TextMatcher.Matches(s"(?s).*labels:.*semver-$level.*".r))

      //todo: dhall needs to be updated
      def steward(extra: Matcher) = Rule(
        "scala-steward",
        Matcher.Many(
          List(
            Matcher.Author(TextMatcher.Matches("(scala_steward)|(michal.pawlik)|(j.kozlowski)".r)),
            Matcher.PipelineStatus("SUCCESS"),
            extra
          )
        ),
        Action.Merge
      )

      val anyLibraryPatch = semver("patch")

      val fromWMS = Matcher.Description(TextMatcher.Matches("""(?s).*((com\.ocado\.ospnow\.wms)|(com\.ocado\.gm\.wms))(?s).*""".r))

      val wmsLibraryMinor =
        semver("minor").and(fromWMS)

      val config: ProjectConfig = ProjectConfig(
        List(
          anyLibraryPatch,
          wmsLibraryMinor
        ).map(steward)
      )

      def readConfig(project: Project): MergeRequestState => F[ProjectActions.Matched[Unit]] =
        //todo
        s => config.rules.traverse(r => ProjectActions.compileMatcher(r.matcher).matches(s)).map(_.head).pure[F]

    }

  def nixJsonConfig[F[_]: Concurrent: ContextShift](
    blocker: Blocker
  ): F[ProjectConfigReader[F]] = {
    val inputState = """{ status = "success"; author = "user1@gmail.com"; description = "hello werld"; }"""

    def statusAsNix(s: Gitlab.MergeRequestInfo.Status): String = s match {
      case Success      => "success"
      case Other(value) => value
    }

    def asNix(s: MergeRequestState): String =
      //todo escape strings etc.
      s"""{ status = "${statusAsNix(s.status)}"; author = "${s.authorUsername}"; description = "${s.description.getOrElse("")}"; }"""

    def args(state: MergeRequestState) = List("eval", s"(import /dev/stdin) ${asNix(state)}", "--json")
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

    import io.circe.syntax._
    println((Result.NotOk(NonEmptyList.one(Mismatch.Author(TextRule.Equal("scala_steward")))): Result).asJson.noSpaces)
    val instance: ProjectConfigReader[F] = new ProjectConfigReader[F] {
      import Result._
      implicit val decoder: Decoder[ProjectActions.Matched[Unit]] = Decoder[Result].map {
        case Ok                => Right(())
        case NotOk(mismatches) => Left(??? /* todo */ )
      }

      def readConfig(project: Project): MergeRequestState => F[ProjectActions.Matched[Unit]] = state => {
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

import io.pg.config.circe.circeConfig

@ConfiguredJsonCodec()
sealed trait Result extends Product with Serializable

object Result {
  case object Ok extends Result

  final case class NotOk(mismatches: NonEmptyList[Mismatch]) extends Result
}

@ConfiguredJsonCodec()
sealed trait Mismatch extends Product with Serializable

object Mismatch {
  final case class Status(expected: io.pg.config.Status) extends Mismatch
  final case class Author(expected: TextRule) extends Mismatch
  final case class Description(expected: TextRule) extends Mismatch
  final case class NoneOf(expected: NonEmptyList[Mismatch]) extends Mismatch
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
