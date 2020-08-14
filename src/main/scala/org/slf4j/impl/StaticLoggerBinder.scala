package org.slf4j.impl

import cats.effect.IO
import scala.concurrent.ExecutionContext
import cats.effect.Timer
import io.odin.Logger
import io.odin.formatter.Formatter
import io.odin.slf4j.OdinLoggerBinder
import io.odin.Level
import cats.effect.Clock
import cats.effect.Effect

class StaticLoggerBinder extends OdinLoggerBinder[IO] {
  implicit val F: Effect[IO] = IO.ioEffect
  implicit val clock: Clock[IO] = Clock.create[IO]

  import StaticLoggerBinder.baseLogger

  val loggers: PartialFunction[String, Logger[IO]] = {
    case s if s.startsWith("io.pg") => baseLogger
    case _                          => baseLogger.withMinimalLevel(Level.Info)
  }

}

object StaticLoggerBinder extends StaticLoggerBinder {

  //EC isn't used - only Clock is required
  implicit val timer: Timer[IO] = IO.timer(ExecutionContext.parasitic)
  val baseLogger = io.odin.consoleLogger[IO](formatter = Formatter.colorful)

  val REQUESTED_API_VERSION: String = "1.7"

  def getSingleton: StaticLoggerBinder = this

}
