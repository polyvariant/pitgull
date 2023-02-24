package io.pg

import cats.Applicative
import cats.Monad
import cats.Show
import cats.data.EitherNel
import cats.data.NonEmptyList
import cats.implicits._
import fs2.Pipe
import io.odin.Logger
import io.pg.MergeRequestState
import io.pg.ProjectActions
import io.pg.ProjectActions.Mismatch
import io.pg.StateResolver
import io.pg.config.ProjectConfigReader
import io.pg.gitlab.webhook.Project

trait MergeRequests[F[_]] {

  def build(
    project: Project
  ): F[List[MergeRequestState]]

}

object MergeRequests {

  def apply[F[_]](
    using F: MergeRequests[F]
  ): MergeRequests[F] = F

  import scala.util.chaining._

  def instance[F[_]: ProjectConfigReader: StateResolver: Monad: Logger](
    implicit SC: fs2.Compiler[F, F]
  ): MergeRequests[F] = project =>
    for {
      config <- ProjectConfigReader[F].readConfig(project)
      states <- StateResolver[F]
                  .resolve(project)
                  .flatMap(validActions[F, Mismatch, MergeRequestState, MergeRequestState](ProjectActions.compile(_, config)))
    } yield states

  private def validActions[F[_]: Logger: Applicative, E: Show, A, B](
    compile: A => List[EitherNel[E, B]]
  )(
    states: List[A]
  )(
    implicit SC: fs2.Compiler[F, F]
  ): F[List[B]] = {
    def tapLeftAndDrop[L, R](
      log: L => F[Unit]
    ): Pipe[F, Either[L, R], R] =
      _.evalTap(_.leftTraverse(log)).map(_.toOption).unNone

    val logMismatches: NonEmptyList[E] => F[Unit] = e =>
      Logger[F].info(
        "Ignoring action because it didn't match rules",
        Map("rules" -> e.map(_.toString).mkString_(", "))
      )

    fs2
      .Stream
      .emits(states)
      .flatMap(compile(_).pipe(fs2.Stream.emits(_)))
      .through(tapLeftAndDrop(logMismatches))
      .compile
      .toList
  }

}
