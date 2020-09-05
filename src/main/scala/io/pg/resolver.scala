package io.pg

import cats.data.OptionT
import cats.tagless.finalAlg
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.gitlab.Gitlab
import io.odin.Logger
import cats.MonadError
import cats.syntax.all._
import cats.data.NonEmptyList
import io.pg.gitlab.graphql.MergeRequest
import io.pg.gitlab.graphql.Pipeline
import io.pg.gitlab.graphql.User
import io.pg.gitlab.Gitlab.GitlabError
import io.pg.gitlab.webhook.Project

@finalAlg
trait StateResolver[F[_]] {
  def resolve(event: WebhookEvent): F[Option[MergeRequestState]]
}

object StateResolver {

  //Option - some events don't yield a state to work with and should be ignored.
  //We should get an ADT for this.
  def instance[F[_]: Gitlab: Logger: MonadError[*[_], Throwable]]: StateResolver[F] =
    new StateResolver[F] {

      private def decodeMergeRequest(pipeline: WebhookEvent.Pipeline): OptionT[F, io.pg.gitlab.webhook.MergeRequest] = {
        val project = pipeline.project

        val mergeRequestByHeadPipeline = {
          val mergeRequestIdString: OptionT[F, String] =
            OptionT {
              Gitlab[F]
                .mergeRequests(
                  projectPath = project.pathWithNamespace,
                  sourceBranches = NonEmptyList.of(pipeline.objectAttributes.ref)
                )(
                  MergeRequest.iid ~ MergeRequest.headPipeline(Pipeline.id)
                )
                .map(_.headOption)
            }.collect {
              case (mrIid, Some(headPipelineId)) if headPipelineId === pipeline.objectAttributes.id.toString => mrIid
            }

          mergeRequestIdString
            .flatMap { mrIId =>
              mrIId.toLongOption.toOptionT[F].map(io.pg.gitlab.webhook.MergeRequest(_))
            }
        }

        pipeline.mergeRequest.toOptionT[F].orElse(mergeRequestByHeadPipeline)
      }

      private def findMergeRequestInfo(iid: Long, project: Project): F[(String, Option[String])] =
        Gitlab[F]
          .mergeRequestInfo(projectPath = project.pathWithNamespace, mergeRequestIId = iid.toString) {
            val authorEmail = MergeRequest
              .author(User.email.map(_.liftTo[F](GitlabError("MR author's email missing"))))
              .map(_.liftTo[F](GitlabError("MR author missing")))

            authorEmail ~ MergeRequest.description
          }
          .flatMap(_.leftSequence)
          .flatMap(_.leftSequence)

      def resolve(event: WebhookEvent): F[Option[MergeRequestState]] =
        event match {
          case p: WebhookEvent.Pipeline =>
            val project = p.project

            decodeMergeRequest(p)
              .semiflatMap(mr => findMergeRequestInfo(mr.iid, project).tupleRight(mr))
              .map {
                case ((email, description), mr) =>
                  MergeRequestState(
                    projectId = project.id,
                    mergeRequestIid = mr.iid,
                    authorEmail = email,
                    description = description,
                    successful = p.objectAttributes.status === WebhookEvent.Pipeline.Status.Success
                  )
              }
              .value

          case e                        => Logger[F].info("Ignoring event", Map("event" -> e.toString())).as(none)
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
  successful: Boolean
)
