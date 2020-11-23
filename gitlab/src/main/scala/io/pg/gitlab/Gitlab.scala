package io.pg.gitlab

import ciris.Secret
import cats.syntax.all._
import sttp.model.Uri
import sttp.client.Request
import sttp.client.SttpBackend
import sttp.client.NothingT
import sttp.tapir.Endpoint
import cats.MonadError
import cats.tagless.finalAlg
import io.pg.gitlab.graphql.Project
import io.pg.gitlab.graphql.MergeRequest
import caliban.client.SelectionBuilder
import caliban.client.Operations.IsOperation
import io.pg.gitlab.graphql.Query
import scala.util.chaining._
import cats.data.NonEmptyList
import io.pg.gitlab.graphql.MergeRequestConnection
import io.pg.gitlab.graphql.MergeRequestState
import io.odin.Logger

@finalAlg
trait Gitlab[F[_]] {

  def mergeRequestInfo[A](
    projectPath: String,
    mergeRequestIId: String
  )(
    selection: SelectionBuilder[MergeRequest, A]
  ): F[A]

  def mergeRequests[A](
    projectPath: String
  )(
    selection: SelectionBuilder[MergeRequest, A]
  ): F[List[A]]

  def acceptMergeRequest(projectId: Long, mergeRequestIid: Long): F[Unit]
}

object Gitlab {

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
      def mergeRequestInfo[A](
        projectPath: String,
        mergeRequestIId: String
      )(
        selection: SelectionBuilder[MergeRequest, A]
      ): F[A] =
        Query
          .project(projectPath)(
            Project
              .mergeRequest(mergeRequestIId)(selection)
              .map(_.liftTo[F](GitlabError("MR not found")))
          )
          .map(_.liftTo[F](GitlabError("Project not found")))
          .pipe(runGraphQLQuery(_))
          .flatten
          .flatten

      def mergeRequests[A](
        projectPath: String
      )(
        selection: SelectionBuilder[graphql.MergeRequest, A]
      ): F[List[A]] =
        Logger[F].info(
          "Finding merge requests",
          Map(
            "projectPath" -> projectPath
          )
        ) *> Query
          .project(projectPath)(
            Project
              .mergeRequests(
                state = MergeRequestState.opened.some
              )(
                MergeRequestConnection
                  .nodes(selection)
                  .map(_.toList.flatMap(_.toList))
              )
              .map(_.toList.flatten.flattenOption)
          )
          .map(_.liftTo[F](new Throwable("Project not found")))
          .pipe(runGraphQLQuery(_))
          .flatten
          .flatTap { result =>
            Logger[F].info(
              "Found merge requests",
              Map("result" -> result.mkString)
            )
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
