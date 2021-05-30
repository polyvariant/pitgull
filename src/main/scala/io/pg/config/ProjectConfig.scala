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

import java.nio.file.Paths
import scala.util.chaining._

@finalAlg
trait ProjectConfigReader[F[_]] {
  def readConfig(project: Project): F[ProjectConfig]
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

      def readConfig(project: Project): F[ProjectConfig] = config.pure[F]
    }

  def nixJsonConfig[F[_]: Concurrent: ContextShift](
    blocker: Blocker
  ): F[ProjectConfigReader[F]] = {
    val input = """{ status = "success"; author = "user1@gmail.com"; description = "hello werld"; }"""
    val args = List("eval", s"(import /dev/stdin) $input", "--json")
    //todo: not reading a local file
    val filePath = "./wms.nix"

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

      def readConfig(project: Project): F[ProjectConfig] =
        Process(nixCommand)
          .fromFile(Paths.get(filePath))
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
