package io.pg

import org.scalatest.wordspec.AsyncWordSpec
import io.pg.gitlab.transport.WebhookEvent

class MainTest extends AsyncWordSpec {
  println(WebhookEvent.codec)

}
