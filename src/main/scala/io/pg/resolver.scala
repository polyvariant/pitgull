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

@finalAlg
trait StateResolver[F[_]] {
  def resolve(event: WebhookEvent): F[Option[MergeRequestState]]
}

object StateResolver {

  //Option - some events don't yield a state to work with and should be ignored.
  //We should get an ADT for this.
  def instance[F[_]: Gitlab: Logger: MonadError[*[_], Throwable]]: StateResolver[F] =
    // Implementation note: all effectful methods here can fail with Halt,
    // which should be handled gracefully as a reason for an incomplete state.
    new StateResolver[F] {
      private case class Halt(msg: String) extends Throwable

      private def halt[A](msg: String): F[A] = Halt(msg).raiseError[F, A]

      private def orHalt[A](msg: String)(opt: Option[A]): F[A] = opt.fold[F[A]](halt(msg))(_.pure[F])

      private def unhalt[A]: F[A] => F[Either[String, A]] =
        _.map(_.asRight[String]).recover {
          case Halt(msg) => msg.asLeft[A]
        }

      private def decodeMergeRequest(pipeline: WebhookEvent.Pipeline): F[io.pg.gitlab.webhook.MergeRequest] = {
        val project = pipeline.project

        val mergeRequestByHeadPipeline: F[io.pg.gitlab.webhook.MergeRequest] = {
          val PipelineId = pipeline.objectAttributes.id.toString

          val query = Gitlab[F]
            .mergeRequests(
              projectPath = project.pathWithNamespace,
              sourceBranches = NonEmptyList.of(pipeline.objectAttributes.ref)
            )(
              MergeRequest.iid ~ MergeRequest.headPipeline(Pipeline.id)
            )

          query
            .flatMap(_.headOption.pipe(orHalt("No open MRs found for branch")))
            .flatTap {
              case (mrIid, Some(s"gid://gitlab/Ci::Pipeline/$PipelineId")) => Applicative[F].unit
              case (_, None)                                               => halt[Unit]("MR didn't have a head pipeline")
              case _                                                       => halt[Unit]("Head pipeline didn't match event's pipeline ID")
            }
            .map { case (mrIid, _) => mrIid }
            .flatMap(_.toLongOption.pipe(orHalt("MR id wasn't a Long")))
            .map(io.pg.gitlab.webhook.MergeRequest(_))
        }
        pipeline.mergeRequest.pipe(orHalt("Webhook event is missing MR information")).handleErrorWith {
          case Halt(_) => mergeRequestByHeadPipeline
        }
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
              .flatMap(mr => findMergeRequestInfo(mr.iid, project).tupleRight(mr))
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
              .pipe(unhalt)
              .flatTap {
                case Left(reason) => Logger[F].debug("Couldn't build MR state", Map("reason" -> reason))
                case Right(state) => Logger[F].info("Resolved MR state", Map("state" -> state.toString))
              }
              .map(_.toOption)

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
  status: WebhookEvent.Pipeline.Status
)
