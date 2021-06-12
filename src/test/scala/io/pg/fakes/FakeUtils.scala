package io.pg.fakes

import cats.Monad
import cats.mtl.Stateful
import cats.effect.Ref

object FakeUtils {

  def statefulRef[F[_]: Monad, A](ref: Ref[F, A]): Stateful[F, A] =
    new Stateful[F, A] {
      def monad: Monad[F] = implicitly
      def get: F[A] = ref.get
      def set(s: A): F[Unit] = ref.set(s)
      override def modify(f: A => A): F[Unit] = ref.update(f)
    }

}
