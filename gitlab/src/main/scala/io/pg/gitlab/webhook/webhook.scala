package io.pg.gitlab.webhook

import io.circe.generic.extras._
import io.circe.generic.extras.semiauto._
import io.circe.Codec
import io.circe.Decoder
import cats.syntax.all._
import io.circe.Encoder
import cats.kernel.Eq

object CirceConfiguration {

  implicit val config: Configuration =
    Configuration
      .default
      .withSnakeCaseMemberNames
      .withSnakeCaseConstructorNames
      .withDiscriminator("object_kind")

}

import CirceConfiguration._

@ConfiguredJsonCodec
sealed trait WebhookEvent

object WebhookEvent {
  private type MR = io.pg.gitlab.webhook.MergeRequest

  final case class Pipeline(
    mergeRequest: Option[MR],
    project: Project,
    objectAttributes: Pipeline.Attributes
  ) extends WebhookEvent

  object Pipeline {
    @ConfiguredJsonCodec
    final case class Attributes(id: Long, ref: String, status: Status)

    sealed trait Status

    object Status {
      case object Success extends Status
      case class Other(value: String) extends Status

      implicit val eq: Eq[Status] = Eq.fromUniversalEquals

      implicit val codec: Codec[Status] = Codec.from(
        Decoder
          .decodeLiteralString["success"]
          .as(Success)
          .or(Decoder.decodeString.map(Other)),
        Encoder[String].contramap {
          case Success      => "success"
          case Other(value) => value
        }
      )

    }

  }

  final case class Push(project: Project) extends WebhookEvent
  final case class MergeRequest(project: Project) extends WebhookEvent
}

@ConfiguredJsonCodec
final case class MergeRequest(iid: Long /* , state: MergeRequest.State */ )

object MergeRequest {
  sealed trait State

  object State {
    case object Opened extends State
    case object Closed extends State
    case object Locked extends State //?
    case object Merged extends State

    implicit val codec: Codec[State] = deriveEnumerationCodec
  }

}

@ConfiguredJsonCodec
final case class Project(
  id: Long,
  name: String,
  pathWithNamespace: String,
  defaultBranch: String
)
