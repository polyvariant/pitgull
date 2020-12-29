package io.pg.fakes

import cats.mtl.Stateful
import monocle.macros.Lenses
import io.pg.ProjectActions
import io.pg.StateResolver
import io.pg.ProjectAction
import io.pg.MergeRequestState
import io.pg.gitlab.webhook.Project
import io.pg.ProjectAction.Merge
import io.scalaland.chimney.dsl._
import cats.implicits._
import cats.FlatMap
import cats.effect.Sync
import cats.effect.concurrent.Ref
import io.pg.gitlab.Gitlab.MergeRequestInfo
import io.pg.MergeRequestState.Mergeability

object ProjectActionsStateFake {
  sealed case class MergeRequestDescription(projectId: Long, mergeRequestIid: Long)

  object MergeRequestDescription {
    val fromMergeAction: ProjectAction.Merge => MergeRequestDescription = _.transformInto[MergeRequestDescription]
  }

  @Lenses
  sealed case class State(
    mergeRequests: Map[MergeRequestDescription, MergeRequestState]
  )

  object State {
    val initial: State = State(Map.empty)

    /**  A collection of modifiers on the state, which will be provided together with the instance using it.
      */
    trait Modifiers[F[_]] {
      // returns Iid of created MR
      def open(projectId: Long, authorEmail: String, description: Option[String]): F[Long]
      def finishPipeline(projectId: Long, mergeRequestIid: Long): F[Unit]
    }

  }

  type Data[F[_]] = Stateful[F, State]
  def Data[F[_]: Data]: Data[F] = implicitly[Data[F]]

  def refInstance[F[_]: Sync]: F[ProjectActions[F] with StateResolver[F] with State.Modifiers[F]] =
    Ref[F].of(State(Map.empty)).map(FakeUtils.statefulRef(_)).map(implicit F => instance[F])

  /** This instance has both the capabilities of ProjectActions and StateResolver,
    * because they operate on the same state, and the state is sealed by convention.
    */
  def instance[
    F[_]: Data: FlatMap
  ]: ProjectActions[F] with StateResolver[F] with State.Modifiers[F] = new ProjectActions[F] with StateResolver[F] with State.Modifiers[F] {

    def execute(action: ProjectAction): F[Unit] = action match {
      case m: Merge => merge(m)
    }

    private def merge(action: ProjectAction.Merge): F[Unit] =
      Data[F].modify(State.mergeRequests.modify(_ - MergeRequestDescription.fromMergeAction(action)))

    def resolve(project: Project): F[List[MergeRequestState]] =
      Data[F].get.map(_.mergeRequests).map(_.values.toList)

    def open(projectId: Long, authorEmail: String, description: Option[String]): F[Long] = {

      val getNextId = Data[F]
        .get
        .map(
          _.mergeRequests
            .values
            .filter(_.projectId === projectId)
            .map(_.mergeRequestIid)
            .maxOption
            .getOrElse(0L) + 1L
        )

      getNextId.flatTap { newId =>
        val initState = MergeRequestState(
          projectId = projectId,
          mergeRequestIid = newId,
          authorEmail = authorEmail.some,
          description = description,
          status = MergeRequestInfo.Status.Other("Created"),
          mergeability = Mergeability.CanMerge
        )

        Data[F].modify {
          State.mergeRequests.modify {
            _ + (MergeRequestDescription(projectId, newId) -> initState)
          }
        }
      }
    }

    def finishPipeline(projectId: Long, mergeRequestIid: Long): F[Unit] =
      Data[F].modify {
        State.mergeRequests.modify {
          val key = MergeRequestDescription(projectId, mergeRequestIid)

          _.updatedWith(key) {
            _.map { state =>
              state.copy(status = MergeRequestInfo.Status.Success)
            }
          }
        }
      }

  }

}
