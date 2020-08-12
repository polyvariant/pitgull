package org.slf4j.impl

import cats.effect.ConcurrentEffect
import cats.effect.ContextShift
import cats.effect.IO
import scala.concurrent.ExecutionContext
import cats.effect.Timer
import io.odin.Logger
import io.odin.formatter.Formatter
import io.odin.slf4j.OdinLoggerBinder
import io.odin.Level

class StaticLoggerBinder extends OdinLoggerBinder[IO] {
  val ec: ExecutionContext = ExecutionContext.global

  implicit val timer: Timer[IO] = IO.timer(ec)
  implicit val cs: ContextShift[IO] = IO.contextShift(ec)
  implicit val F: ConcurrentEffect[IO] = IO.ioConcurrentEffect

  import StaticLoggerBinder.baseLogger

  val loggers: PartialFunction[String, Logger[IO]] = {
    case s if s.startsWith("io.pg") => baseLogger
    case _                          => baseLogger.withMinimalLevel(Level.Info)
  }

}

object StaticLoggerBinder extends StaticLoggerBinder {

  val baseLogger = io.odin.consoleLogger[IO](formatter = Formatter.colorful)

  var REQUESTED_API_VERSION: String = "1.7"

  def getSingleton: StaticLoggerBinder = this

}
