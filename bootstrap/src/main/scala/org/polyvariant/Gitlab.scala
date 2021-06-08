package org.polyvariant

import cats.implicits.*


import scala.util.chaining._
import io.pg.gitlab.graphql.*
import sttp.model.Uri
import sttp.client3.*
import caliban.client.SelectionBuilder
import caliban.client.CalibanClientError.DecodingError
import io.pg.gitlab.graphql.MergeRequest
import io.pg.gitlab.graphql.MergeRequestConnection
import io.pg.gitlab.graphql.MergeRequestState
import io.pg.gitlab.graphql.Pipeline
import io.pg.gitlab.graphql.PipelineStatusEnum
import io.pg.gitlab.graphql.Project
import io.pg.gitlab.graphql.ProjectConnection
import io.pg.gitlab.graphql.Query
import io.pg.gitlab.graphql.UserCore
import cats.MonadError
import caliban.client.Operations.IsOperation

trait Gitlab[F[_]] {
  def mergeRequests(projectId: Long): F[List[Gitlab.MergeRequestInfo]]
}
object Gitlab {

  def sttpInstance[F[_]: Logger](
    baseUri: Uri,
    accessToken: String
  )(
    using backend: SttpBackend[Identity, Any], // FIXME: all cats-effect compatible backends rely on Netty, while netty breaks native-image build
    // using backend: SttpBackend[F, Any],
    ME: MonadError[F, Throwable]
  ): Gitlab[F] = {
    def runRequest[O](request: Request[O, Any]): F[O] =
      request.header("Private-Token", accessToken).send(backend).pure[F].map(_.body) // FIXME - change to async backend
    
    def runGraphQLQuery[A: IsOperation, B](a: SelectionBuilder[A, B]): F[B] =
      runRequest(a.toRequest(baseUri.addPath("api", "graphql"))).rethrow
    

    new Gitlab[F] {
      def mergeRequests(projectId: Long): F[List[MergeRequestInfo]] =
        Logger[F].info(s"Looking up merge requests for project: $projectId") *> 
        mergeRequestsQuery(projectId)
          .mapEither(_.toRight(DecodingError("Project not found")))
          .pipe(runGraphQLQuery(_))
          .flatTap { result =>
            Logger[F].info(s"Found merge requests. Size: ${result.size}")
          }
    }

  }
  
  final case class MergeRequestInfo(
    projectId: Long,
    mergeRequestIid: Long,
    authorUsername: String,
    description: Option[String],
    needsRebase: Boolean,
    hasConflicts: Boolean
  )

  private def flattenTheEarth[A]: Option[List[Option[Option[Option[List[Option[A]]]]]]] => List[A] =
    _.toList.flatten.flatten.flatten.flatten.flatten.flatten

  private def mergeRequestInfoSelection(projectId: Long): SelectionBuilder[MergeRequest, MergeRequestInfo] = (
    MergeRequest.iid.mapEither(_.toLongOption.toRight(DecodingError("MR IID wasn't a Long"))) ~
      MergeRequest
        .author(UserCore.username)
        .mapEither(_.toRight(DecodingError("MR has no author"))) ~
      MergeRequest.description ~
      MergeRequest.shouldBeRebased ~
      MergeRequest.conflicts
  ).mapN((buildMergeRequest(projectId) _))

  private def buildMergeRequest(
    projectId: Long
  )(
    mergeRequestIid: Long,
    authorUsername: String,
    description: Option[String],
    needsRebase: Boolean,
    hasConflicts: Boolean
  ): MergeRequestInfo = MergeRequestInfo(
    projectId = projectId,
    mergeRequestIid = mergeRequestIid,
    authorUsername = authorUsername,
    description = description,
    needsRebase = needsRebase,
    hasConflicts = hasConflicts
  )        

  private def mergeRequestsQuery(projectId: Long) = 
    Query
      .projects(ids = List(show"gid://gitlab/Project/$projectId").some)(
        ProjectConnection
          .nodes(
            Project
              .mergeRequests(
                state = MergeRequestState.opened.some
              )(
                MergeRequestConnection
                  .nodes(mergeRequestInfoSelection(projectId))
              )
          )
          .map(flattenTheEarth)
      )
}