package io.pg

import cats.MonadError
import cats.syntax.all._
import cats.tagless.finalAlg
import io.odin.Logger
import io.pg.gitlab.Gitlab
import io.pg.gitlab.Gitlab.MergeRequestInfo
import io.pg.gitlab.webhook.Project
import io.scalaland.chimney.dsl._

@finalAlg
trait StateResolver[F[_]] {
  def resolve(project: Project): F[List[MergeRequestState]]
}

object StateResolver {

  def instance[F[_]: Gitlab: Logger: MonadError[*[_], Throwable]](
    implicit SC: fs2.Stream.Compiler[F, F]
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
        mr
          .into[MergeRequestState]
          .withFieldComputed(_.status, _.status.getOrElse(MergeRequestInfo.Status.Success)) //for now - no pipeline means success
          .transform

      def resolve(project: Project): F[List[MergeRequestState]] =
        findMergeRequests(project)
          .flatTap { state =>
            Logger[F].info("Resolved MR state", Map("state" -> state.toString))
          }

    }

}

//current MR state - rebuilt on every event.
//Checked against rules to come up with a decision.
final case class MergeRequestState(
  projectId: Long,
  mergeRequestIid: Long,
  authorEmail: Option[String],
  description: Option[String],
  status: MergeRequestInfo.Status
)
