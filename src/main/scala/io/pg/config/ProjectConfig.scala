package io.pg.config

import cats.Applicative
import cats.effect.Blocker
import cats.effect.Concurrent
import cats.effect.ContextShift
import cats.effect.ExitCode
import cats.effect.Sync
import cats.syntax.all._
import cats.tagless.finalAlg
import io.github.vigoo.prox.ProxFS2
import io.pg.MergeRequestState
import io.pg.ProjectActions
import io.pg.config.Mismatch.Author
import io.pg.config.Mismatch.Description
import io.pg.config.Mismatch.NoneMatched
import io.pg.gitlab.webhook.Project
import io.pg.nix.Nix

import java.nio.file.Paths
import scala.util.chaining._

@finalAlg
trait ProjectConfigReader[F[_]] {
  //ideally this will have F outside, or something
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

    def args(state: MergeRequestState): List[String] = {
      import Nix.syntax._

      val theArg =
        Nix
          .builtins
          .fetchurl
          .applied(
            Nix.obj(
              "url" := "http://localhost:8081/wms.nix",
              //todo: prefetch-url to determine this sha
              "sha256" := "036x1g6b8j8mlp3ndyjwdsrn71v8bx3lsnqiri1ydrv49wrkvp9m"
            )
          )
          .imported
          .applied(state.toNix)

      List("eval", theArg.render, "--json")
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

      //todo: remove these and the existing scala DSL, use the in-nix model everywhere
      def convertTextMismatch(actual: String): TextRule => ProjectActions.Mismatch = {
        case TextRule.Equal(v)   => ProjectActions.Mismatch.ValueMismatch(v, actual)
        case TextRule.Matches(r) => ProjectActions.Mismatch.RegexMismatch(r, actual)
      }

      def convertMismatch(m: Mismatch): ProjectActions.Mismatch = m match {
        case Mismatch.Status(expected, actual) => ProjectActions.Mismatch.ValueMismatch(expected.value, actual.value).atPath("status")
        case Author(expected, actual)          => convertTextMismatch(actual)(expected).atPath("author")
        case Description(expected, actual)     => convertTextMismatch(actual)(expected).atPath("description")
        case NoneMatched(expected)             =>
          //todo: unnecessary wrapping in List in this model
          ProjectActions.Mismatch.ManyFailed(List(expected.map(convertMismatch)))
      }

      val convert: Result => ProjectActions.Matched[Unit] = {
        case Ok                => Right(())
        case NotOk(mismatches) => Left(mismatches.map(convertMismatch))
      }

      def readConfig(project: Project): MergeRequestState => F[ProjectActions.Matched[Unit]] = state =>
        Process("nix", args(state))
          .toFoldMonoid(fs2.text.utf8Decode[F])
          .fromStream(fs2.io.file.readAll[F](Paths.get("./wms.nix"), blocker, 4096), flushChunks = false)
          .run()
          .pipe(checkExitCode)
          .map(_.output)
          .flatTap(out => Sync[F].delay(println(out)))
          .flatMap(io.circe.parser.decode[Result](_).liftTo[F])
          .map(convert)
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
