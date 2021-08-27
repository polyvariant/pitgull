package io.pg

import cats.effect.IO
import cats.effect.kernel.Sync
import cats.effect.std.Dispatcher
import cats.effect.unsafe.implicits._
import io.odin.Logger
import io.odin.slf4j.OdinLoggerBinder
import java.util.concurrent.atomic.AtomicReference

class OdinInterop extends OdinLoggerBinder[IO] {
  implicit val F: Sync[IO] = IO.asyncForIO

  implicit val dispatcher: Dispatcher[IO] = Dispatcher[IO].allocated.unsafeRunSync()._1

  val loggers: PartialFunction[String, Logger[IO]] = {
    val theLogger: String => Option[Logger[IO]] = _ => OdinInterop.globalLogger.get()

    theLogger.unlift
  }

}

object OdinInterop {
  val globalLogger: AtomicReference[Option[Logger[IO]]] = new AtomicReference[Option[Logger[IO]]](None)
}
