package org.polyvariant

import cats.syntax.apply
import cats.effect.kernel.Sync
import scala.io.AnsiColor._

trait Logger[F[_]] {

  def debug(
    msg: String
  ): F[Unit]

  def success(
    msg: String
  ): F[Unit]

  def info(
    msg: String
  ): F[Unit]

  def warn(
    msg: String
  ): F[Unit]

  def error(
    msg: String
  ): F[Unit]

}

object Logger {

  def apply[F[_]](
    using ev: Logger[F]
  ): Logger[F] = ev

  def wrappedPrint[F[_]: Sync] = new Logger[F] {

    private def colorPrinter(
      color: String
    )(
      msg: String
    ): F[Unit] =
      Sync[F].delay(println(s"$color$msg$RESET"))

    override def debug(
      msg: String
    ): F[Unit] = colorPrinter(CYAN)(msg)

    override def success(
      msg: String
    ): F[Unit] = colorPrinter(GREEN)(msg)

    override def info(
      msg: String
    ): F[Unit] = colorPrinter(WHITE)(msg)

    override def warn(
      msg: String
    ): F[Unit] = colorPrinter(YELLOW)(msg)

    override def error(
      msg: String
    ): F[Unit] = colorPrinter(RED)(msg)

  }

}
