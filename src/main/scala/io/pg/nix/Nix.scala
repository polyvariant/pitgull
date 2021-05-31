package io.pg.nix

import cats.tagless.autoContravariant
import java.nio.file.{Path => JavaPath}

// A minimal Nix expression AST for our needs
sealed trait Nix extends Product with Serializable {
  def at(key: String): Map[RecordEntry, Nix] = Map(RecordEntry(key) -> this)
  def render: String = Nix.render(this)
}

object Nix {
  final case class Record(entries: Map[RecordEntry, Nix]) extends Nix
  final case class Str(value: String) extends Nix
  final case class Path(value: JavaPath) extends Nix

  @autoContravariant
  trait From[A] {
    def toNix(a: A): Nix
  }

  object From {
    def apply[A](implicit A: From[A]): From[A] = A

    implicit val stringToNix: From[String] = Str(_)
    implicit val pathToNix: From[JavaPath] = Path(_)
  }

  // todo: some tests
  def render(expr: Nix): String = {
    def renderEntry(e: RecordEntry): String = e.key

    expr match {
      case Record(entries) => s"""{${entries.map { case (k, v) => s"${renderEntry(k)} = ${render(v)}" }.mkString(" ", "; ", "; ")}}"""
      case Str(value)      =>
        val doubleTick = "''"
        val quote = "\""

        def escape(str: String) = str.replaceAll("\\\"", "\\\"")

        if (value.contains("\n")) doubleTick ++ value ++ doubleTick
        else quote ++ escape(value) ++ quote

      case Path(p) => p.toString
    }
  }

  object syntax {

    implicit final class AnyToNix[A](private val self: A) extends AnyVal {
      def toNix(implicit F: From[A]): Nix = F.toNix(self)
    }

  }

}

final case class RecordEntry(key: String)
