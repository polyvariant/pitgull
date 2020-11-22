package io.pg

import cats.tagless.finalAlg
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.gitlab.Gitlab
import io.odin.Logger
import cats.MonadError
import cats.syntax.all._
import io.pg.gitlab.graphql.MergeRequest
import io.pg.gitlab.graphql.Pipeline
import io.pg.gitlab.graphql.User
import io.pg.gitlab.Gitlab.GitlabError
import io.pg.gitlab.graphql.PipelineStatusEnum
import cats.kernel.Eq
import io.pg.gitlab.webhook.Project

@finalAlg
trait StateResolver[F[_]] {
  def resolve(event: WebhookEvent): F[List[MergeRequestState]]
}

object StateResolver {

  def instance[F[_]: Gitlab: Logger: MonadError[*[_], Throwable]](
    implicit SC: fs2.Stream.Compiler[F, F]
  ): StateResolver[F] =
    new StateResolver[F] {

      private def findMergeRequests(project: Project): fs2.Stream[F, MergeRequestState] = {
        def buildState(
          mergeRequestIidF: F[Long],
          pipelineStatus: Option[PipelineStatusEnum],
          authorEmailF: F[String],
          description: Option[String]
        ): F[MergeRequestState] =
          (mergeRequestIidF, authorEmailF).mapN { (mergeRequestIid, authorEmail) =>
            MergeRequestState(
              projectId = project.id,
              mergeRequestIid = mergeRequestIid,
              authorEmail = authorEmail,
              description = description,
              status = MergeRequestState
                .Status
                .fromPipelineStatus(
                  pipelineStatus.getOrElse(PipelineStatusEnum.SUCCESS /* i guess */ )
                )
            )
          }

        val statesQuery = Gitlab[F]
          .mergeRequests(
            projectPath = project.pathWithNamespace
          ) {
            (
              MergeRequest.iid.map(_.toLongOption.liftTo[F](GitlabError("MR IID wasn't a Long"))) ~
                MergeRequest
                  .headPipeline(Pipeline.status) ~
                MergeRequest
                  .author(User.email.map(_.liftTo[F](GitlabError("MR author's email missing"))))
                  .map(_.liftTo[F](GitlabError("MR author missing")).flatten) ~
                MergeRequest.description
            ).mapN(buildState _)
          }

        fs2.Stream.evals(statesQuery).evalMap(identity)
      }

      def resolve(event: WebhookEvent): F[List[MergeRequestState]] =
        findMergeRequests(event.project)
          .evalTap { state =>
            Logger[F].info("Resolved MR state", Map("state" -> state.toString))
          }
          .compile
          .toList

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
