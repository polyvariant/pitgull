package io.pg.gitlab

import ciris.Secret
import cats.implicits._
import sttp.model.Uri
import sttp.client.Request
import sttp.client.SttpBackend
import sttp.client.NothingT
import sttp.tapir.Endpoint

trait Gitlab[F[_]] {
  def acceptMergeRequest(projectId: Int, mergeRequestIid: Int): F[Unit]
}

object Gitlab {

  def sttpInstance[F[_]: MonadError[*[_], Throwable]](
    baseUri: Uri,
    accessToken: Secret[String]
  )(
    implicit backend: SttpBackend[F, Nothing, NothingT]
  ): Gitlab[F] =
    new Gitlab[F] {
      import sttp.tapir.client.sttp._

      private def runRequest[O](request: Request[O, Nothing]): F[O] =
        //todo multiple possible header names...
        request.header("Private-Token", accessToken.value).send[F]().map(_.body)

      private def runInfallibleEndpoint[I, O](endpoint: Endpoint[I, Nothing, O, Nothing]): I => F[O] =
        i => runRequest(new RichEndpoint[I, Nothing, O, Nothing](endpoint).toSttpRequestUnsafe(baseUri).apply(i)).map(_.merge)

      def acceptMergeRequest(projectId: Int, mergeRequestIid: Int): F[Unit] =
        runInfallibleEndpoint(GitlabEndpoints.acceptMergeRequest).apply((projectId, mergeRequestIid)).void
    }

}

object GitlabEndpoints {
  import sttp.tapir._
  import sttp.tapir.json.circe._

  private val baseEndpoint = infallibleEndpoint.in("api" / "v4")

  val acceptMergeRequest: Endpoint[(Int, Int), Nothing, Unit, Nothing] =
    baseEndpoint
      //hehe putin
      .put
      .in("projects" / path[Int]("id") / "merge_requests" / path[Int]("merge_request_iid") / "merge")
      .out(jsonBody[Unit])

}
