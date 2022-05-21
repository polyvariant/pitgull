package io.pg.messaging

import cats.effect.std.Queue
import scala.reflect.ClassTag
import cats.syntax.all._
import cats.ApplicativeError
import io.odin.Logger
import cats.Functor
import cats.Invariant
import cats.ApplicativeThrow

trait Publisher[F[_], -A] {
  def publish(a: A): F[Unit]
}

final case class Processor[F[_], -A](process: fs2.Pipe[F, A, Unit])

object Processor {

  def simple[F[_]: ApplicativeThrow: Logger, A](
    f: A => F[Unit]
  ): Processor[F, A] =
    Processor[F, A] {
      _.evalMap { msg =>
        f(msg).handleErrorWith(logError[F, A](msg))
      }
    }

  def logError[F[_]: Logger, A](msg: A): Throwable => F[Unit] =
    e =>
      Logger[F].error(
        "Encountered error while processing message",
        Map("message" -> msg.toString()),
        e
      )

}

trait Channel[F[_], A] extends Publisher[F, A] {
  def consume: fs2.Stream[F, A]
}

object Channel {

  given [F[_]]: Invariant[Channel[F, *]] with {

    def imap[A, B](chan: Channel[F, A])(f: A => B)(g: B => A): Channel[F, B] = new {
      def consume: fs2.Stream[F, B] = chan.consume.map(f)
      def publish(b: B): F[Unit] = chan.publish(g(b))
    }

  }

  def fromQueue[F[_]: Functor, A](q: Queue[F, A]): Channel[F, A] =
    new Channel[F, A] {
      def publish(a: A): F[Unit] = q.offer(a)
      val consume: fs2.Stream[F, A] = fs2.Stream.fromQueueUnterminated(q)
    }

  implicit class ChannelOpticsSyntax[F[_], A](val ch: Channel[F, A]) extends AnyVal {

    /** Transforms a channel into one that forwards everything to the publisher, but only consumes a subset of the original channel's
      * messages (the ones that match `f`).
      */
    def prism[B](f: PartialFunction[A, B])(g: B => A): Channel[F, B] =
      new Channel[F, B] {
        def publish(a: B): F[Unit] = ch.publish(g(a))
        val consume: fs2.Stream[F, B] = ch.consume.collect(f)
      }

    /** Limits a channel to a subtype of its message type.
      */
    def only[B <: A: ClassTag]: Channel[F, B] =
      ch.prism { case b: B =>
        b
      }(identity)

  }

}
