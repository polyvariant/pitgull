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
final case class WebhookEvent(project: Project)

@ConfiguredJsonCodec
final case class Project(
  id: Long,
  name: String,
  pathWithNamespace: String,
  defaultBranch: String
)
