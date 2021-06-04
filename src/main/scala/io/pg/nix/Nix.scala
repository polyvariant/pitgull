package io.pg.nix

import cats.Applicative
import cats.MonadThrow
import cats.tagless.autoContravariant
import org.http4s.Uri
import org.http4s.client.Client

// A minimal Nix expression AST for our needs
sealed trait Nix extends Product with Serializable {
  def at(key: String): Map[RecordEntry, Nix] = Map(RecordEntry(key) -> this)
  def imported: Nix.Import = Nix.Import(this)
  def applied(arg: Nix): Nix.Apply = Nix.Apply(this, arg)
  def select(key: String): Nix.Select = Nix.Select(this, key)

  def render: String = Nix.render(this)
}

object Nix {
  final case class Import(source: Nix) extends Nix
  final case class Record(entries: Map[RecordEntry, Nix]) extends Nix
  final case class Name(value: String) extends Nix
  final case class Select(selectee: Nix, selector: String) extends Nix
  final case class Str(value: String) extends Nix
  final case class Apply(function: Nix, parameter: Nix) extends Nix
  final case class Function(param: Name, body: Nix) extends Nix
  final case class ApplyBinaryOperator(lhs: Nix, rhs: Nix, op: Operator) extends Nix
  //this could be an enum or sth
  final case class Operator(value: String) extends Nix

  val plus = Operator("+")

  @autoContravariant
  trait From[-A] {
    def toNix(a: A): Nix
  }

  object From {
    def apply[A](implicit A: From[A]): From[A] = A

    implicit val stringToNix: From[String] = Str(_)
    implicit val self: From[Nix] = identity(_)
  }

  object builtins {
    val fetchurl: Select = Name("builtins").select("fetchurl")
  }

  import cats.implicits._

  def fail(msg: String) = throw new Exception(msg)

  def parse[F[_]: Applicative](s: String): F[Nix] = {
    val x = Nix.Name("x")
    s.trim match {
      case "x: x + x" => Nix.Function(x, Nix.ApplyBinaryOperator(x, x, plus)).pure[F].widen
      case _          => fail(s"parser only supports a simple case. Actual input first 10 chars: ${s.take(10)}")
    }
  }

  final case class VirtualPath(content: String)

  def interpret[F[_]: MonadThrow](expr: Nix)(implicit client: Client[F], C: fs2.Compiler[F, F]): F[Any] = {
    def fetchurl(arg: Nix): F[String] = {
      println(s"inside fetchUrl with args $arg")
      // todo: use sha
      val url = arg match {
        case Record(entries) => entries(RecordEntry("url")).asInstanceOf[Nix.Str].value
        case e               => fail(s"I don't wanna evaluate this rn: $e")
      }

      Uri
        .fromString(url)
        .liftTo[F]
        .flatMap { uri =>
          client.get[String](uri)(_.bodyText.compile.string)
        }
    }
    def eval(expr: Nix, symbols: Map[Name, Nix]): F[Any] = {
      println(s"entered eval of $expr")

      def resolveImport(expr: Nix): F[Nix] = {
        println(s"attempting to resolve $expr")

        eval(expr, symbols).flatMap {
          //could also be a path but yknow
          //actually, a fetched file is probably just a /nix/store path
          case vp: VirtualPath =>
            // .flatMap(parse[F]).flatMap(eval(_, Map.empty))
            println(s"evalated to virtual path containing $vp")
            parse[F](vp.content).widen
          case e               => fail(s"evaluated to unexpected kind of thing: $e")
        }
      }

      def resolveAsFunction(f: Nix): F[Nix => F[Any]] = {
        println("entered resolveAsFunction")
        f match {
          case Function(param, body)      =>
            ((input: Nix) => eval(body, symbols ++ Map(param -> input))).pure[F]
          case Import(source)             =>
            resolveImport(source).map { result =>
              println("resolved import of " + source + s" to $result")
              (input => eval(result.applied(input), Map.empty /* symbols should be known for input but idk */ ))
            }
          case Select(selectee, selector) =>
            eval(selectee, symbols).flatMap { evald =>
              println(s"evaluated selectee as $evald")

              //hell yeah type erasure
              if (
                selector == "fetchurl" && evald.isInstanceOf[Map[_, _]] && evald
                  .asInstanceOf[Map[String, String]]
                  .get("fetchurl")
                  .contains("fetchurl-internal")
              ) ((arg: Nix) => fetchurl(arg).map(VirtualPath(_)).widen[Any]).pure[F]
              else fail(s"unknown selector: $selector")
            }

        }
      }

      expr match {
        case Nix.ApplyBinaryOperator(lhs, rhs, op) =>
          op match {
            case `plus` =>
              (eval(lhs, symbols).map(_.asInstanceOf[String]), eval(rhs, symbols).map(_.asInstanceOf[String])).mapN(_ + _)
            case _      => fail(s"unknown operator: $op")
          }

        case Import(source)             => resolveImport(source).widen
        case Record(entries)            =>
          entries
            .toList
            .traverse { case (k, v) =>
              eval(v, symbols).tupleLeft(k.key)
            }
            .map(_.toMap)
        case n @ Name(value)            =>
          if (value == "builtins") Map("fetchurl" -> "fetchurl-internal").pure[F].widen
          else if (symbols.contains(n)) eval(symbols(n), Map.empty).widen
          else fail("unknown name: " + value)
        case Select(selectee, selector) => fail("select")
        case Str(value)                 => value.pure[F].widen
        case Apply(function, parameter) => resolveAsFunction(function).flatMap(_.apply(parameter))
        case Function(_, _)             =>
          fail("can't eval raw function")
      }
    }

    eval(expr, Map.empty)
  }

  def obj(entries: (RecordEntry, Nix)*): Record = Record(entries.toMap)

  def render(expr: Nix): String = {
    def renderEntry(e: RecordEntry): String = e.key

    expr match {
      case Record(entries) => s"""{ ${entries.map { case (k, v) => s"${renderEntry(k)} = ${render(v)}; " }.mkString}}"""
      case Str(value)      =>
        val doubleTick = "''"
        val quote = "\""

        def escape(str: String) = str.replaceAll("\\\"", "\\\"")

        if (value.contains("\n")) doubleTick ++ value ++ doubleTick
        else quote ++ escape(value) ++ quote

      case Name(n)                    => n
      case Select(selectee, selector) =>
        val selecteeNeedsParens = selectee match {
          case Import(_) | Apply(_, _) => true
          case _                       => false
        }

        s"${parenthesizeOptional(selectee.render, selecteeNeedsParens)}.$selector"

      case Apply(function, arg) =>
        val functionNeedsParens = function match {
          case Import(_) => true
          case _         => false
        }

        val maybeWrapFunction = parenthesizeOptional(function.render, functionNeedsParens)
        val maybeWrapArg = parenthesizeOptional(arg.render, needsParensGeneric(arg))

        s"$maybeWrapFunction $maybeWrapArg"

      case Import(source) =>
        s"import ${parenthesizeOptional(source.render, needsParensGeneric(source))}"
    }
  }

  private val needsParensGeneric: Nix => Boolean = {
    case Import(_) | Apply(_, _) => true
    case _                       => false
  }

  private def parenthesizeOptional(s: String, shouldParenthesize: Boolean): String = if (shouldParenthesize) s"($s)" else s

  object syntax {

    implicit final class AnyToNix[A](private val self: A) extends AnyVal {
      def toNix(implicit F: From[A]): Nix = F.toNix(self)
    }

    implicit final class StringToNixKey(private val key: String) extends AnyVal {
      def :=[A: From](value: A): (RecordEntry, Nix) = RecordEntry(key) -> value.toNix
    }

  }

}

final case class RecordEntry(key: String)
