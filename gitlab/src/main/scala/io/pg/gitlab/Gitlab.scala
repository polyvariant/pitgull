package io.pg.gitlab

import scala.util.chaining._

import cats.MonadError
import cats.syntax.all._
import cats.tagless.finalAlg
import ciris.Secret
import io.odin.Logger
import io.pg.gitlab.graphql.MergeRequest
import io.pg.gitlab.graphql.MergeRequestConnection
import io.pg.gitlab.graphql.MergeRequestState
import io.pg.gitlab.graphql.Project
import io.pg.gitlab.graphql.ProjectConnection
import sttp.client.NothingT
import sttp.client.Request
import sttp.client.SttpBackend
import sttp.model.Uri
import sttp.tapir.Endpoint
import caliban.client.Operations.IsOperation
import caliban.client.SelectionBuilder

@finalAlg
trait Gitlab[F[_]] {

  def mergeRequests[A](
    projectId: Long
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
      import caliban.client.Argument
      import caliban.client.FieldBuilder.Obj
      import caliban.client.FieldBuilder.OptionOf
      import caliban.client.Operations

      //todo: replace with Query.projects from caliban-gitlab when it can be released
      def projects[A](
        ids: Option[List[String]] = None
      )(
        innerSelection: SelectionBuilder[ProjectConnection, A]
      ): SelectionBuilder[Operations.RootQuery, Option[A]] =
        caliban
          .client
          .SelectionBuilder
          .Field(
            "projects",
            OptionOf(Obj(innerSelection)),
            arguments = List(
              Argument("ids", ids)
            )
          )

      def mergeRequests[A](
        projectId: Long
      )(
        selection: SelectionBuilder[graphql.MergeRequest, A]
      ): F[List[A]] =
        Logger[F].info(
          "Finding merge requests",
          Map(
            "projectId" -> projectId.show
          )
        ) *> projects(ids = List(show"gid://gitlab/Project/$projectId").some)(
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
          .map(_.liftTo[F](GitlabError("Project not found")))
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
