package io.pg.gitlab.webhook

import io.circe.generic.extras._

private object CirceConfiguration {

  implicit val config: Configuration =
    Configuration
      .default
      .withSnakeCaseMemberNames
      .withSnakeCaseConstructorNames
      .withDiscriminator("object_kind")

}

import CirceConfiguration._

@ConfiguredJsonCodec
final case class WebhookEvent(project: Project, objectKind: String /* for logs */ )

@ConfiguredJsonCodec
final case class Project(
  id: Long,
  pathWithNamespace: String
)

object Project {
  val demo = Project(20190338, "kubukoz/demo")
}
