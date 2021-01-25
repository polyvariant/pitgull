package io.pg.webhook

import cats.Functor
import cats.MonadError
import cats.data.NonEmptyList
import cats.implicits._
import io.odin.Logger
import io.pg.MergeRequests
import io.pg.ProjectActions
import io.pg.gitlab.webhook.Project
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.messaging.Publisher
import io.pg.transport
import io.scalaland.chimney.dsl._
import sttp.tapir.server.ServerEndpoint

object WebhookRouter {

  object endpoints {
    import sttp.tapir._
    import sttp.tapir.json.circe._
    import sttp.tapir.generic.auto._

    private val webhookEndpoint = infallibleEndpoint.in("webhook")

    val webhook =
      webhookEndpoint.post.in(jsonBody[WebhookEvent])

    val preview =
      webhookEndpoint.get.in("preview" / path[Long]("projectId").map(Project(_))(_.id)).out(jsonBody[List[transport.MergeRequestState]])
  }

  def routes[F[_]: MergeRequests: Functor](
    implicit eventPublisher: Publisher[F, WebhookEvent]
  ): NonEmptyList[ServerEndpoint[_, _, _, Any, F]] =
    NonEmptyList.of(
      endpoints.webhook.serverLogicRecoverErrors(eventPublisher.publish),
      endpoints.preview.serverLogicRecoverErrors(MergeRequests[F].build(_).nested.map(_.transformInto[transport.MergeRequestState]).value)
    )

}

object WebhookProcessor {

  def instance[
    F[
      _
    ]: MergeRequests: ProjectActions: Logger: MonadError[*[
      _
    ], Throwable]
  ]: WebhookEvent => F[Unit] = { ev =>
    for {
      _      <- Logger[F].info("Received event", Map("event" -> ev.toString()))
      states <- MergeRequests[F].build(ev.project)

      nextMR = states.minByOption(_.mergeability)
      _      <- Logger[F].info("Considering MR for action", Map("mr" -> nextMR.show))
      action <- nextMR.flatTraverse(ProjectActions[F].resolve(_))
      _      <- action.traverse(ProjectActions[F].execute(_))
    } yield ()
  }

}
