package io.pg

object Prelude {

  implicit class AnythingAnything[A](private val a: A) extends AnyVal {
    def ??? : Nothing = ???
  }

}
