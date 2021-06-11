package org.polyvariant

import cats.effect.kernel.Sync
import scala.io.StdIn.{readChar => unsafeReadChar}

trait Console[F[_]] {
  def readChar(): F[Char]
}

object Console {
  def apply[F[_]](using ev: Console[F]): Console[F] = ev

  def instance[F[_]: Sync] = new Console[F] {
    def readChar(): F[Char] = 
      Sync[F].delay(unsafeReadChar)
  }
}
