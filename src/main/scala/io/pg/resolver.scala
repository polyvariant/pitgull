package io.pg

import caliban.client.CalibanClientError.DecodingError
import cats.MonadError
import cats.kernel.Eq
import cats.syntax.all._
import cats.tagless.finalAlg
import io.odin.Logger
import io.pg.gitlab.Gitlab
import io.pg.gitlab.graphql.MergeRequest
import io.pg.gitlab.graphql.Pipeline
import io.pg.gitlab.graphql.PipelineStatusEnum
import io.pg.gitlab.graphql.User
import io.pg.gitlab.webhook.Project

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
          .mergeRequests(
            projectId = project.id
          ) {
            (
              MergeRequest.iid.mapEither(_.toLongOption.toRight(DecodingError("MR IID wasn't a Long"))) ~
                MergeRequest.headPipeline(Pipeline.status) ~
                MergeRequest
                  .author(User.publicEmail.mapEither(_.toRight(DecodingError("MR author's email missing"))))
                  .mapEither(_.toRight(DecodingError("MR author missing"))) ~
                MergeRequest.description
            ).mapN(buildState(project.id) _)
          }

      private def buildState(
        projectId: Long
      )(
        mergeRequestIid: Long,
        pipelineStatus: Option[PipelineStatusEnum],
        authorEmail: String,
        description: Option[String]
      ): MergeRequestState =
        MergeRequestState(
          projectId = projectId,
          mergeRequestIid = mergeRequestIid,
          authorEmail = authorEmail,
          description = description,
          status = MergeRequestState
            .Status
            .fromPipelineStatus(
              pipelineStatus.getOrElse(PipelineStatusEnum.SUCCESS /* i guess */ )
            )
        )

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
  authorEmail: String,
  description: Option[String],
  status: MergeRequestState.Status
)

object MergeRequestState {
  sealed trait Status extends Product with Serializable

  object Status {
    case object Success extends Status
    final case class Other(value: String) extends Status

    val fromPipelineStatus: PipelineStatusEnum => Status = {
      case PipelineStatusEnum.SUCCESS => Success
      case other                      => Other(other.toString)
    }

    implicit val eq: Eq[Status] = Eq.fromUniversalEquals

  }

}
