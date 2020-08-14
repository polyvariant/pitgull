package io.pg

import org.scalatest.wordspec.AsyncWordSpec
import cats.effect.IO
import cats.implicits._
import ciris.Secret

class MainTest extends AsyncWordSpec {
  println(WebhookEvent.codec)
  "Application" should {
    "start" in {
      val testConfig = AppConfig(
        HttpConfig(8080),
        MetaConfig("-", BuildInfo.version, BuildInfo.scalaVersion),
        Git(Git.Host.Gitlab, "http://localhost", Secret("token"))
      )

      Main.serve(testConfig).use(IO.pure).as(succeed).unsafeToFuture()
    }
  }
}
