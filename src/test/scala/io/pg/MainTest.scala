package io.pg

import org.scalatest.wordspec.AsyncWordSpec
import cats.effect.IO
import cats.implicits._
import ciris.Secret
import sttp.client._

class MainTest extends AsyncWordSpec {
  "Application" should {
    "start" in {
      val testConfig = AppConfig(
        http = HttpConfig(8080),
        meta = MetaConfig("-", BuildInfo.version, BuildInfo.scalaVersion),
        git = Git(Git.Host.Gitlab, uri"http://localhost", Secret("token")),
        queues = Queues(10)
      )

      Main.serve(testConfig).use(IO.pure).as(succeed).unsafeToFuture()
    }
  }
}
