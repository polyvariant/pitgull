package io.pg.gitlab.webhook

import io.circe.Codec

final case class WebhookEvent(project: Project, objectKind: String /* for logs */ )

object WebhookEvent {
  // todo: use configured codec when https://github.com/circe/circe/pull/1800 is available
  given Codec[WebhookEvent] = Codec.forProduct2("project", "object_kind")(apply)(we => (we.project, we.objectKind))
}

final case class Project(
  id: Long
) derives Codec.AsObject

object Project {
  val demo = Project(20190338)
}
