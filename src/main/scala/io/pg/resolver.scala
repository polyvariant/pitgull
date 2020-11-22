package io.pg

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
import scala.util.chaining._
import cats.Applicative
import io.pg.Halt

@finalAlg
trait StateResolver[F[_]] {
  def resolve(event: WebhookEvent): F[List[MergeRequestState]]
}

object StateResolver {

  //Option - some events don't yield a state to work with and should be ignored.
  //We should get an ADT for this.
  def instance[
    F[_]: Gitlab: Logger: MonadError[*[_], Throwable]: Halt
  ]: StateResolver[F] =
    // Implementation note: all effectful methods here can fail with Halt,
    // which should be handled gracefully as a reason for an incomplete state.
    new StateResolver[F] {

      private def decodeMergeRequest(
        pipeline: WebhookEvent.Pipeline
      ): F[io.pg.gitlab.webhook.MergeRequest] = {
        val project = pipeline.project
        val PipelineId = pipeline.objectAttributes.id.toString

        val query = Gitlab[F]
          .mergeRequests(
            projectPath = project.pathWithNamespace
          )(
            MergeRequest.iid ~ MergeRequest.headPipeline(Pipeline.id)
          )

        query
          .flatMap(
            _.headOption.pipe(Halt[F].orCease("No open MRs found"))
          )
          .flatTap {
            case (mrIid, Some(s"gid://gitlab/Ci::Pipeline/$PipelineId")) =>
              Applicative[F].unit
            case (_, None)                                               =>
              Halt[F].cease[Unit]("MR didn't have a head pipeline")
            case _                                                       =>
              Halt[F].cease[Unit](
                "Head pipeline didn't match event's pipeline ID"
              )
          }
          .map { case (mrIid, _) => mrIid }
          .flatMap(
            _.toLongOption.pipe(Halt[F].orCease("MR id wasn't a Long"))
          )
          .map(io.pg.gitlab.webhook.MergeRequest(_))
      }

      private def findMergeRequestInfo(
        iid: Long,
        project: Project
      ): F[(String, Option[String])] =
        Gitlab[F]
          .mergeRequestInfo(
            projectPath = project.pathWithNamespace,
            mergeRequestIId = iid.toString
          ) {
            val authorEmail = MergeRequest
              .author(
                User
                  .email
                  .map(_.liftTo[F](GitlabError("MR author's email missing")))
              )
              .map(_.liftTo[F](GitlabError("MR author missing")))

            authorEmail ~ MergeRequest.description
          }
          .flatMap(_.leftSequence)
          .flatMap(_.leftSequence)

      def resolve(event: WebhookEvent): F[List[MergeRequestState]] =
        event match {
          case p: WebhookEvent.Pipeline =>
            val project = p.project

            val result: F[List[MergeRequestState]] = decodeMergeRequest(p)
              .flatMap(mr =>
                findMergeRequestInfo(mr.iid, project).tupleRight(mr)
              )
              .map {
                case ((email, description), mr) =>
                  MergeRequestState(
                    projectId = project.id,
                    mergeRequestIid = mr.iid,
                    authorEmail = email,
                    description = description,
                    status = p.objectAttributes.status
                  )
              }
              .pipe(
                Halt[F].decease { reason =>
                  Logger[F]
                    .debug("Couldn't build MR state", Map("reason" -> reason))
                }
              )
              .flatTap { state =>
                Logger[F].info(
                  "Resolved MR state",
                  Map("state" -> state.toString)
                )
              }
              .map(_.toList)

            result

          case e                        =>
            Logger[F]
              .info("Ignoring event", Map("event" -> e.toString()))
              .as(Nil)
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
  status: WebhookEvent.Pipeline.Status
)
