package io.pg

import cats.tagless.finalAlg
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.gitlab.Gitlab
import io.pg.Prelude._
import io.odin.Logger
import cats.MonadError
import cats.syntax.all._
import io.pg.gitlab.graphql.MergeRequest
import io.pg.gitlab.graphql.Pipeline
import io.pg.gitlab.graphql.User
import io.pg.gitlab.Gitlab.GitlabError
import io.pg.gitlab.webhook.Project

@finalAlg
trait StateResolver[F[_]] {
  def resolve(event: WebhookEvent): F[List[MergeRequestState]]
}

object StateResolver {

  //Option - some events don't yield a state to work with and should be ignored.
  //We should get an ADT for this.
  def instance[F[_]: Gitlab: Logger: MonadError[*[_], Throwable]](
    implicit SC: fs2.Stream.Compiler[F, F]
  ): StateResolver[F] =
    new StateResolver[F] {

      private def findMergeRequests(
        pipeline: WebhookEvent.Pipeline
      ): fs2.Stream[F, io.pg.gitlab.webhook.MergeRequest] = {
        val project = pipeline.project
        val PipelineId = pipeline.objectAttributes.id.toString

        val query = Gitlab[F]
          .mergeRequests(
            projectPath = project.pathWithNamespace
          )(
            MergeRequest.iid ~ MergeRequest.headPipeline(Pipeline.id)
          )

        fs2
          .Stream
          .evals(query)
          .evalFilter[F] {
            //todo: ignore pipeline id. Attach pipeline status in some different way
            case (mrIid, Some(s"gid://gitlab/Ci::Pipeline/$PipelineId")) =>
              true.pure[F]

            case (_, None)                                               =>
              Logger[F].info("MR didn't have a head pipeline").as(false)
            case _                                                       =>
              Logger[F]
                .info("Head pipeline didn't match event's pipeline ID")
                .as(false)
          }
          .map { case (mrIid, _) => mrIid }
          .evalMap(
            _.toLongOption.liftTo[F](new Throwable("MR id wasn't a Long"))
          )
          .map(io.pg.gitlab.webhook.MergeRequest(_))
      }

      private def findMergeRequestInfo(
        iid: Long,
        project: Project
      ): F[(String @@ "author email", Option[String] @@ "mr description")] =
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

            findMergeRequests(p)
              .evalMap(mr =>
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
              .evalTap { state =>
                Logger[F].info(
                  "Resolved MR state",
                  Map("state" -> state.toString)
                )
              }
              .compile
              .toList

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
