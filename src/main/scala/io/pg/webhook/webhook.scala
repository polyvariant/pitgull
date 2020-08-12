package io.pg.webhook

import cats.data.NonEmptyList
import sttp.tapir.server.ServerEndpoint
import cats.implicits._
import io.circe.Codec
import io.pg.webhook.WebhookRouter.transport.WebhookEvent

object WebhookRouter {

  def routes[F[_]: Applicative]: NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] = {
    import sttp.tapir._
    import sttp.tapir.json.circe._

    val webhook = infallibleEndpoint.post.in("webhook").in(jsonBody[WebhookEvent]).out(jsonBody[String])

    NonEmptyList.of(
      webhook.serverLogicRecoverErrors(b => ("Successful! Got this hot body: " + b).pure[F])
    )
  }

  import io.circe.generic.extras._
  import io.circe.generic.extras.semiauto._

  implicit val config: Configuration =
    Configuration.default.withSnakeCaseMemberNames.withSnakeCaseConstructorNames

  object transport {
    final case class WebhookEvent(project: Project, objectKind: WebhookEvent.ObjectKind)

    object WebhookEvent {

      sealed trait ObjectKind extends Product with Serializable

      object ObjectKind {
        case object Push extends ObjectKind
        case object MergeRequest extends ObjectKind

        implicit val codec: Codec[ObjectKind] = deriveEnumerationCodec
      }

      implicit val codec: Codec[WebhookEvent] = deriveConfiguredCodec

    }

    final case class Project(
      id: Int /* todo: apparently tapir has a conflict in schemas for Long */,
      name: String,
      pathWithNamespace: String,
      defaultBranch: String,
      url: String
    )

    object Project {

      implicit val codec: Codec[Project] = deriveConfiguredCodec
    }

  }

}
