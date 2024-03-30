package io.pg

import cats.MonadError
import cats.implicits._
import cats.kernel.Order
import io.odin.Logger
import io.pg.gitlab.Gitlab
import io.pg.gitlab.Gitlab.MergeRequestInfo
import io.pg.gitlab.webhook.Project
import cats.Show
import cats.MonadThrow
import monocle.syntax.all._

trait StateResolver[F[_]] {
  def resolve(project: Project): F[List[MergeRequestState]]
}

object StateResolver {

  def apply[F[_]](
    using F: StateResolver[F]
  ): StateResolver[F] = F

  def instance[F[_]: Gitlab: Logger: MonadThrow](
    implicit SC: fs2.Compiler[F, F]
  ): StateResolver[F] =
    new StateResolver[F] {

      private def findMergeRequests(project: Project): F[List[MergeRequestState]] =
        Gitlab[F]
          .mergeRequests(projectId = project.id)
          .nested
          .map(buildState)
          .value

      private def buildState(
        mr: MergeRequestInfo
      ): MergeRequestState =
        MergeRequestState(
          projectId = mr.projectId,
          mergeRequestIid = mr.mergeRequestIid,
          authorUsername = mr.authorUsername,
          description = mr.description,
          status = mr.status.getOrElse(MergeRequestInfo.Status.Success),
          mergeability = MergeRequestState
            .Mergeability
            .fromFlags(
              hasConflicts = mr.hasConflicts,
              needsRebase = mr.needsRebase
            )
        )

      def resolve(project: Project): F[List[MergeRequestState]] =
        findMergeRequests(project)
          .flatTap { state =>
            Logger[F].info("Resolved MR state", Map("state" -> state.show))
          }

    }

}

//current MR state - rebuilt on every event.
//Checked against rules to come up with a decision.
final case class MergeRequestState(
  projectId: Long,
  mergeRequestIid: Long,
  authorUsername: String,
  description: Option[String],
  status: MergeRequestInfo.Status,
  mergeability: MergeRequestState.Mergeability
)

object MergeRequestState {
  sealed trait Mergeability extends Product with Serializable

  object Mergeability {
    case object CanMerge extends Mergeability
    case object NeedsRebase extends Mergeability
    case object HasConflicts extends Mergeability

    def fromFlags(hasConflicts: Boolean, needsRebase: Boolean): Mergeability =
      if (hasConflicts) HasConflicts
      else if (needsRebase) NeedsRebase
      else CanMerge

    implicit val order: Order[Mergeability] = Order.by(List(CanMerge, NeedsRebase, HasConflicts).indexOf)
  }

  implicit val showTrimmed: Show[MergeRequestState] =
    _.focus(_.description).modify(_.map(TextUtils.trim(maxChars = 80))).toString
}
