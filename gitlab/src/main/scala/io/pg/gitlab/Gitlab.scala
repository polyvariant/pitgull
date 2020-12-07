package io.pg.gitlab

import scala.util.chaining._

import caliban.client.CalibanClientError.DecodingError
import caliban.client.Operations.IsOperation
import caliban.client.SelectionBuilder
import cats.MonadError
import cats.kernel.Eq
import cats.syntax.all._
import cats.tagless.finalAlg
import ciris.Secret
import io.odin.Logger
import io.pg.gitlab.Gitlab.MergeRequestInfo
import io.pg.gitlab.graphql.MergeRequest
import io.pg.gitlab.graphql.MergeRequestConnection
import io.pg.gitlab.graphql.MergeRequestState
import io.pg.gitlab.graphql.Pipeline
import io.pg.gitlab.graphql.PipelineStatusEnum
import io.pg.gitlab.graphql.Project
import io.pg.gitlab.graphql.ProjectConnection
import io.pg.gitlab.graphql.Query
import io.pg.gitlab.graphql.User
import sttp.client.NothingT
import sttp.client.Request
import sttp.client.SttpBackend
import sttp.model.Uri
import sttp.tapir.Endpoint

@finalAlg
trait Gitlab[F[_]] {
  def mergeRequests(projectId: Long): F[List[MergeRequestInfo]]
  def acceptMergeRequest(projectId: Long, mergeRequestIid: Long): F[Unit]
}

object Gitlab {

  // VCS-specific MR information
  // Not specific to the method of fetching (no graphql model references etc.)
  // Fields only required according to reason (e.g. must have a numeric ID - we might loosen this later)
  final case class MergeRequestInfo(
    projectId: Long,
    mergeRequestIid: Long,
    status: Option[MergeRequestInfo.Status],
    authorEmail: Option[String],
    description: Option[String]
  )

  object MergeRequestInfo {
    sealed trait Status extends Product with Serializable

    object Status {
      case object Success extends Status
      final case class Other(value: String) extends Status

      implicit val eq: Eq[Status] = Eq.fromUniversalEquals
    }

  }

  def sttpInstance[F[_]: Logger: MonadError[*[_], Throwable]](
    baseUri: Uri,
    accessToken: Secret[String]
  )(
    implicit backend: SttpBackend[F, Nothing, NothingT]
  ): Gitlab[F] = {

    def runRequest[O](request: Request[O, Nothing]): F[O] =
      //todo multiple possible header names...
      request.header("Private-Token", accessToken.value).send[F]().map(_.body)

    import sttp.tapir.client.sttp._

    def runEndpoint[I, E, O](
      endpoint: Endpoint[I, E, O, Nothing]
    ): I => F[Either[E, O]] =
      i => runRequest(endpoint.toSttpRequestUnsafe(baseUri).apply(i))

    def runInfallibleEndpoint[I, O](
      endpoint: Endpoint[I, Nothing, O, Nothing]
    ): I => F[O] =
      runEndpoint[I, Nothing, O](endpoint).nested.map(_.merge).value

    def runGraphQLQuery[A: IsOperation, B](a: SelectionBuilder[A, B]): F[B] =
      runRequest(a.toRequest(baseUri.path("api", "graphql"))).rethrow

    new Gitlab[F] {
      def mergeRequests(projectId: Long): F[List[MergeRequestInfo]] = {
        val selection: SelectionBuilder[MergeRequest, MergeRequestInfo] = (
          MergeRequest.iid.mapEither(_.toLongOption.toRight(DecodingError("MR IID wasn't a Long"))) ~
            MergeRequest.headPipeline(Pipeline.status) ~
            MergeRequest
              .author(User.publicEmail)
              .mapEither(_.toRight(DecodingError("MR has no author"))) ~
            MergeRequest.description
        ).mapN(buildMergeRequest(projectId) _)

        Logger[F].info(
          "Finding merge requests",
          Map(
            "projectId" -> projectId.show
          )
        ) *> Query
          .projects(ids = List(show"gid://gitlab/Project/$projectId").some)(
            ProjectConnection
              .nodes(
                Project
                  .mergeRequests(
                    state = MergeRequestState.opened.some
                  )(
                    MergeRequestConnection
                      .nodes(selection)
                      .map(_.toList.flatMap(_.toList).flatten)
                  )
              )
              // o boi, here I come flattening again
              .map(_.toList.flatMap(_.flatMap(_.flatten.toList.flatten)))
          )
          .mapEither(_.toRight(DecodingError("Project not found")))
          .pipe(runGraphQLQuery(_))
          .flatTap { result =>
            Logger[F].info(
              "Found merge requests",
              Map("result" -> result.mkString)
            )
          }
      }
      private def buildMergeRequest(
        projectId: Long
      )(
        mergeRequestIid: Long,
        pipelineStatus: Option[PipelineStatusEnum],
        authorEmail: Option[String],
        description: Option[String]
      ): MergeRequestInfo = MergeRequestInfo(
        projectId = projectId,
        mergeRequestIid = mergeRequestIid,
        status = pipelineStatus.map(convertPipelineStatus),
        authorEmail = authorEmail,
        description = description
      )

      private val convertPipelineStatus: PipelineStatusEnum => MergeRequestInfo.Status = {
        case PipelineStatusEnum.SUCCESS => MergeRequestInfo.Status.Success
        case other                      => MergeRequestInfo.Status.Other(other.toString)
      }

      def acceptMergeRequest(projectId: Long, mergeRequestIid: Long): F[Unit] =
        runInfallibleEndpoint(GitlabEndpoints.acceptMergeRequest)
          .apply((projectId, mergeRequestIid))
          .void
    }
  }

  final case class GitlabError(msg: String) extends Throwable(s"Gitlab error: $msg")
}

object GitlabEndpoints {
  import sttp.tapir._

  private val baseEndpoint = infallibleEndpoint.in("api" / "v4")

  val acceptMergeRequest: Endpoint[(Long, Long), Nothing, Unit, Nothing] =
    baseEndpoint
      //hehe putin
      .put
      .in(
        "projects" / path[Long]("id") / "merge_requests" / path[Long](
          "merge_request_iid"
        ) / "merge"
      )

}
