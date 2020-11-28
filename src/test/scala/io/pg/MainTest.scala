package io.pg

import cats.effect.IO
import ciris.Secret
import sttp.client._
import weaver.SimpleIOSuite

object MainTest extends SimpleIOSuite {
  test("Application starts") {
    val testConfig = AppConfig(
      http = HttpConfig(8080),
      meta = MetaConfig("-", BuildInfo.version, BuildInfo.scalaVersion),
      git = Git(Git.Host.Gitlab, uri"http://localhost", Secret("token")),
      queues = Queues(10)
    )

    Main.serve(testConfig).use(IO.pure).as(success)
  }
}
