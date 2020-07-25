package io.pg

import org.scalatest.wordspec.AsyncWordSpec
import cats.effect.IO

class MainTest extends AsyncWordSpec {
  "Application" should {
    "start" in {
      Main.serve.use(IO.pure).as(succeed).unsafeToFuture()
    }
  }
}
