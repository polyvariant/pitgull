package org.slf4j.impl

import java.util.concurrent.atomic.AtomicReference

import cats.effect.Clock
import cats.effect.Effect
import cats.effect.IO
import io.odin.Logger
import io.odin.slf4j.OdinLoggerBinder

class StaticLoggerBinder extends OdinLoggerBinder[IO] {
  implicit val F: Effect[IO] = IO.ioEffect
  implicit val clock: Clock[IO] = Clock.create[IO]

  val loggers: PartialFunction[String, Logger[IO]] = {
    val theLogger: String => Option[Logger[IO]] = _ => StaticLoggerBinder.globalLogger.get()

    theLogger.unlift
  }

}

object StaticLoggerBinder extends StaticLoggerBinder {
  val globalLogger = new AtomicReference[Option[Logger[IO]]](None)

  //EC isn't used - only Clock is required
  val REQUESTED_API_VERSION: String = "1.7"

  def getSingleton: StaticLoggerBinder = this

}
