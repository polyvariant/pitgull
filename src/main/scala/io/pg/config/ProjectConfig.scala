package io.pg.config

import cats.Applicative
import cats.MonadThrow
import cats.effect.ExitCode
import cats.syntax.all._
import cats.tagless.finalAlg
import io.github.vigoo.prox.ProxFS2
import io.pg.gitlab.webhook.Project
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

      val anyLibraryPatch = steward(semver("patch"))

      val fromWMS = Matcher.Description(TextMatcher.Matches("""(?s).*((com\.ocado\.ospnow\.wms)|(com\.ocado\.gm\.wms))(?s).*""".r))

      val wmsLibraryMinor = steward(
        semver("minor").and(fromWMS)
      )

      val config: ProjectConfig = ProjectConfig(
        List(
          anyLibraryPatch,
          wmsLibraryMinor
        )
      )

      def readConfig(project: Project): F[ProjectConfig] = config.pure[F]
    }

  def dhallJsonStringConfig[F[_]: ProxFS2: MonadThrow]: F[ProjectConfigReader[F]] = {
    val prox: ProxFS2[F] = implicitly
    import prox._

    val dhallCommand = "dhall-to-json"
    //todo: not reading a local file
    val filePath = "./example.dhall"

    def checkExitCode[O, E]: F[ProcessResult[O, E]] => F[ProcessResult[O, E]] =
      _.ensure(new Throwable("Invalid exit code"))(
        _.exitCode == ExitCode.Success
      )

    implicit val runner: ProcessRunner[JVMProcessInfo] = new JVMProcessRunner

    val instance: ProjectConfigReader[F] = new ProjectConfigReader[F] {

      def readConfig(project: Project): F[ProjectConfig] =
        Process(dhallCommand)
          .`with`("TOKEN" -> "demo-token")
          .fromFile(Paths.get(filePath))
          .toFoldMonoid(fs2.text.utf8Decode[F])
          .run()
          .pipe(checkExitCode)
          .map(_.output)
          .flatMap(io.circe.parser.decode[ProjectConfig](_).liftTo[F])
    }

    val ensureCommandExists =
      Process("bash", "-c" :: s"command -v $dhallCommand" :: Nil)
        .drainOutput(_.drain)
        .run()
        .pipe(checkExitCode)
        .adaptError { case e =>
          new Throwable(s"Command $dhallCommand not found", e)
        }

    ensureCommandExists.as(instance)
  }

}
