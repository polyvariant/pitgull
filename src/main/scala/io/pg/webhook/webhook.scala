package io.pg.webhook

import cats.data.NonEmptyList
import sttp.tapir.server.ServerEndpoint
import cats.implicits._
import io.pg.gitlab.transport.WebhookEvent

object WebhookRouter {

  def routes[F[_]: Applicative]: NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] = {
    import sttp.tapir._
    import sttp.tapir.json.circe._

    val webhook = infallibleEndpoint.post.in("webhook").in(jsonBody[WebhookEvent]).out(jsonBody[String])

    NonEmptyList.of(
      webhook.serverLogicRecoverErrors(b => ("Successful! Got this hot body: " + b).pure[F])
    )
  }

}
