package io.pg

import cats.effect.IO
import ciris.Secret
import sttp.model.Uri._
import weaver.SimpleIOSuite
import cats.arrow.FunctionK

object MainTest extends SimpleIOSuite {
  test("Application starts") {
    val testConfig = AppConfig(
      http = HttpConfig(8080),
      meta = MetaConfig("-", BuildInfo.version, BuildInfo.scalaVersion),
      git = Git(Git.Host.Gitlab, uri"http://localhost", Secret("token")),
      queues = Queues(10),
      middleware = MiddlewareConfig(Set())
    )

    Main.serve(FunctionK.id)(testConfig).use(IO.pure).as(success)
  }
}
