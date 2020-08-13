package io.pg.gitlab.transport

import io.circe.generic.extras._

object CirceConfiguration {

  implicit val config: Configuration =
    Configuration.default.withSnakeCaseMemberNames.withSnakeCaseConstructorNames.withDiscriminator("object_kind")
}

import CirceConfiguration._

@ConfiguredJsonCodec
sealed trait WebhookEvent

object WebhookEvent {
  //wth is going on with the longs!!
  final case class Build(ref: String, buildId: Int, buildName: String, buildStage: String) extends WebhookEvent
  final case class Pipeline() extends WebhookEvent
  final case class Push(project: Project) extends WebhookEvent
  final case class MergeRequest(project: Project) extends WebhookEvent
}

@ConfiguredJsonCodec
final case class Project(
  id: Int /* todo: apparently tapir has a conflict in schemas for Long */,
  name: String,
  pathWithNamespace: String,
  defaultBranch: String,
  url: String
)
