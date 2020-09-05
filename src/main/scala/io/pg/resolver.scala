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

@finalAlg
trait StateResolver[F[_]] {
  def resolve(event: WebhookEvent): F[Option[MergeRequestState]]
}

object StateResolver {

  //Option - some events don't yield a state to work with and should be ignored.
  //We should get an ADT for this.
  def instance[F[_]: Gitlab: Logger: MonadError[*[_], Throwable]]: StateResolver[F] = {
    case p: WebhookEvent.Pipeline =>
      val project = p.project

      val mergeRequestByHeadPipeline = {
        val PipelineId = p.objectAttributes.id.toString

        val mergeRequestByRef =
          OptionT.liftF {
            Logger[F].info(
              "Looking for merge request",
              Map("project" -> project.pathWithNamespace, "sourceBranch" -> p.objectAttributes.ref)
            )
          } *>
            OptionT {
              Gitlab[F]
                .mergeRequests(projectPath = project.pathWithNamespace, sourceBranches = NonEmptyList.of(p.objectAttributes.ref))(
                  MergeRequest.iid ~ MergeRequest.headPipeline(Pipeline.id)
                )
                .map(_.headOption)
            }.semiflatTap {
              case (mrIid, headPipeline) =>
                Logger[F].info("Found merge request", Map("iid" -> mrIid, "headPipeline" -> headPipeline.getOrElse("None")))
            }

        mergeRequestByRef
          .collect {
            case (mrIId, Some(s"gid://gitlab/Ci::Pipeline/$PipelineId")) =>
              mrIId.toLongOption.map(io.pg.gitlab.webhook.MergeRequest(_))
          }
          .subflatMap(identity)
      }

      val mergeRequest: OptionT[F, io.pg.gitlab.webhook.MergeRequest] = p.mergeRequest.toOptionT[F].orElse(mergeRequestByHeadPipeline)

      val stateF = mergeRequest.flatMapF { mr =>
        Gitlab[F]
          .mergeRequestInfo(projectPath = project.pathWithNamespace, mergeRequestIId = mr.iid.toString)(
            MergeRequest.author(User.email) ~ MergeRequest.description
          )
          .flatMap(_.leftTraverse(_.liftTo[F](GitlabError("MR author missing"))))
          .map {
            case (Some(email), description) => MergeRequestState(project.id, mr.iid, email, description).some
            case _                          => none
          }
      }.value

      Option
        .when(
          p.objectAttributes.status === io.pg.gitlab.webhook.WebhookEvent.Pipeline.Status.Success
        )(stateF)
        .flatSequence
    case e                        => Logger[F].info("Ignoring event", Map("event" -> e.toString())).as(none)
  }

}

//current MR state - rebuilt on every event.
//Checked against rules to come up with a decision.
final case class MergeRequestState(projectId: Long, mergeRequestIid: Long, authorEmail: String, description: Option[String])
