package io.pg.fakes

import cats.MonadError
import cats.implicits._
import cats.mtl.Stateful
import io.pg.config.ProjectConfig
import io.pg.config.ProjectConfigReader
import io.pg.gitlab.webhook.Project
import monocle.macros.Lenses
import cats.effect.Sync
import cats.effect.Ref

trait FakeState

object ProjectConfigReaderFake {

  @Lenses
  sealed case class State(
    configs: Map[Long, ProjectConfig]
  )

  object State {
    val initial: State = State(Map.empty)

    /** A collection of modifiers on the state, which will be provided together with the instance using it.
      */
    trait Modifiers[F[_]] {
      def register(projectId: Long, config: ProjectConfig): F[Unit]
    }

  }

  type Data[F[_]] = Stateful[F, State]
  def Data[F[_]: Data]: Data[F] = implicitly[Data[F]]

  def refInstance[F[_]: Sync]: F[ProjectConfigReader[F] with State.Modifiers[F]] =
    Ref[F].of(State(Map.empty)).map(FakeUtils.statefulRef(_)).map(implicit F => instance[F])

  def instance[F[_]: Data: MonadError[*[_], Throwable]]: ProjectConfigReader[F] with State.Modifiers[F] =
    new ProjectConfigReader[F] with State.Modifiers[F] {

      def readConfig(project: Project): F[ProjectConfig] =
        Data[F]
          .get
          .flatMap(_.configs.get(project.id).liftTo[F](new Throwable(s"Unknown project: $project")))

      def register(projectId: Long, config: ProjectConfig): F[Unit] =
        Data[F].modify(State.configs.modify(_ + (projectId -> config)))

    }

}
