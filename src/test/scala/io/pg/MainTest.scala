package io.pg

import org.scalatest.wordspec.AsyncWordSpec
import io.pg.gitlab.transport.WebhookEvent
import scala.util.Try

class MainTest extends AsyncWordSpec {
  def tp(s: => Any) = Try(println(s))
  tp(getClass.getClassLoader().loadClass("io.circe.Decoder"))
  tp(getClass.getClassLoader().loadClass("io.circe.generic.extras.semiauto"))
  tp(getClass.getClassLoader().loadClass("io.circe.generic.extras.Configuration"))
  println(WebhookEvent.codec)

}
