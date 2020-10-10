package io.pg.background

import io.pg.messaging._

sealed trait BackgroundProcess[F[_]] {

  def run: F[Unit] =
    this match {
      case BackgroundProcess.DrainStream(s, c) => s.compile(c).drain
    }

}

object BackgroundProcess {

  final case class DrainStream[F[_], G[_]](
    stream: fs2.Stream[F, Nothing],
    C: fs2.Stream.Compiler[F, G]
  ) extends BackgroundProcess[G]

  def fromStream[F[_], G[_]](
    stream: fs2.Stream[F, _]
  )(
    implicit C: fs2.Stream.Compiler[F, G]
  ): BackgroundProcess[G] =
    new DrainStream(stream.drain, C)

  def fromProcessor[F[_], A](
    channel: Channel[F, A]
  )(
    consumer: Processor[F, A]
  )(
    implicit C: fs2.Stream.Compiler[F, F]
  ): BackgroundProcess[F] =
    fromStream(channel.consume.through(consumer.process))

}
