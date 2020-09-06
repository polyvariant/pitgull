package io.pg

import cats.tagless.finalAlg
import cats.MonadError
import cats.syntax.all._

@finalAlg
trait Halt[F[_]] {
  // fail fast with message
  def cease[A](msg: String): F[A]

  // recover halt using the given handling strategy, get Some if there were no halts
  def decease[A](handle: String => F[Unit])(fa: F[A]): F[Option[A]]

  // convenience method for options - cease if empty
  def orCease[A](msg: String)(opt: Option[A]): F[A]

  // convenience method for effects - fallback to another if halted
  def recease[A](another: F[A])(fa: F[A]): F[A] = receaseWith(_ => another)(fa)

  // convenience method for effects - fallback to another if halted, using message of original halt
  def receaseWith[A](another: String => F[A])(fa: F[A]): F[A]
}

object Halt {

  implicit def instance[F[_]](
    implicit F: MonadError[F, Throwable]
  ): Halt[F] =
    new Halt[F] {
      def cease[A](msg: String): F[A] = Halted(msg).raiseError

      def decease[A](handle: String => F[Unit])(fa: F[A]): F[Option[A]] =
        receaseWith[Option[A]](handle(_).as(none[A]))(fa.map(_.some))

      def orCease[A](msg: String)(opt: Option[A]): F[A] = opt.fold[F[A]](cease(msg))(_.pure[F])

      def receaseWith[A](another: String => F[A])(fa: F[A]): F[A] =
        fa.handleErrorWith {
          case Halted(msg) => another(msg)
        }

    }

  private case class Halted(msg: String) extends Throwable
}
