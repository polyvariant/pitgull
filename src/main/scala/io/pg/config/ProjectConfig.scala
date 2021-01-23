package io.pg.config

import cats.effect.Blocker
import cats.effect.ExitCode
import cats.effect.Concurrent
import cats.effect.ContextShift
import cats.syntax.all._
import java.nio.file.Paths
import io.github.vigoo.prox._
import scala.util.chaining._
import cats.Applicative
import cats.tagless.finalAlg
import io.pg.gitlab.webhook.Project

@finalAlg
trait ProjectConfigReader[F[_]] {
  def readConfig(project: Project): F[ProjectConfig]
}

object ProjectConfigReader {

  def test[F[_]: Applicative]: ProjectConfigReader[F] =
    new ProjectConfigReader[F] {

      //todo: dhall needs to be updated
      val steward = Rule(
        "scala-steward",
        Matcher.Many(
          List(
            Matcher.Author(TextMatcher.Matches("(scala_steward)|(michal.pawlik)|(j.kozlowski)".r)),
            Matcher.Description(TextMatcher.Matches("(?s).*labels:.*semver-patch.*".r)),
            Matcher.PipelineStatus("SUCCESS")
          )
        ),
        Action.Merge
      )

      val config: ProjectConfig = ProjectConfig(
        List(
          steward
          // Rule.mergeAnything
        )
      )

      def readConfig(project: Project): F[ProjectConfig] = config.pure[F]
    }

  def dhallJsonStringConfig[F[_]: Concurrent: ContextShift](
    blocker: Blocker
  ): F[ProjectConfigReader[F]] = {
    val dhallCommand = "dhall-to-json"
    //todo: not reading a local file
    val filePath = "./example.dhall"

    def checkExitCode[O, E]: F[ProcessResult[O, E]] => F[ProcessResult[O, E]] =
      _.ensure(new Throwable("Invalid exit code"))(
        _.exitCode == ExitCode.Success
      )

    implicit val runner: ProcessRunner[F] = new JVMProcessRunner
    val instance: ProjectConfigReader[F] = new ProjectConfigReader[F] {

      def readConfig(project: Project): F[ProjectConfig] = {
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
      Process[F]("bash", "-c" :: s"command -v $dhallCommand" :: Nil)
        .drainOutput(_.drain)
        .run(blocker)
        .pipe(checkExitCode)
        .adaptError { case e =>
          new Throwable(s"Command $dhallCommand not found", e)
        }

    ensureCommandExists.as(instance)
  }

}
