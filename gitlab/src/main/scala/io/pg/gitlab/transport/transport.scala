package io.pg.gitlab.transport

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import io.circe.Codec
import com.kubukoz.DebugUtils

object CirceConfiguration {

  implicit val config: Configuration =
    Configuration.default.withSnakeCaseMemberNames.withSnakeCaseConstructorNames
}

import CirceConfiguration._

final case class WebhookEvent(project: Project, objectKind: WebhookEvent.ObjectKind)

object WebhookEvent {

  sealed trait ObjectKind extends Product with Serializable

  object ObjectKind {
    case object Push extends ObjectKind
    case object MergeRequest extends ObjectKind

    implicit val codec: Codec[ObjectKind] = DebugUtils.withDesugar(deriveEnumerationCodec)
  }

  implicit val codec: Codec[WebhookEvent] = DebugUtils.withDesugar(deriveConfiguredCodec)
}

final case class Project(
  id: Int /* todo: apparently tapir has a conflict in schemas for Long */,
  name: String,
  pathWithNamespace: String,
  defaultBranch: String,
  url: String
)

object Project {
  implicit val codec: Codec[Project] = DebugUtils.withDesugar(deriveConfiguredCodec)
}
