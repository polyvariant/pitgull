package io.pg.config

import cats.implicits._
import scala.util.matching.Regex
import io.circe.Codec
import io.circe.Decoder
import io.circe.Encoder
import io.circe.DecodingFailure
import io.circe.Json

object circe {

  private val decodeRegex: Decoder[Regex] = Decoder.instance {
    _.value
      .asString
      .toRight(DecodingFailure("Failed to decode as String", Nil))
      .flatMap { s =>
        Either.catchNonFatal(s.r).leftMap(DecodingFailure.fromThrowable(_, Nil))
      }
  }

  private val encodeRegex: Encoder[Regex] = Encoder.encodeString.contramap[Regex](_.toString)

  implicit val regexCodec: Codec[Regex] = Codec.from(decodeRegex, encodeRegex)

  def writeWithKind[A: Encoder](a: A, kind: String) = Encoder[A].apply(a).mapObject(_.add("kind", Json.fromString(kind)))
}

import circe.regexCodec
import circe.writeWithKind

enum TextMatcher {
  case Equals(value: String)
  case Matches(regex: Regex)
}

object TextMatcher {

  given Codec[TextMatcher] = {
    given Codec[Equals] = Codec.AsObject.derived
    given Codec[Matches] = Codec.AsObject.derived

    val decoder: Decoder[TextMatcher] = Decoder[String].at("kind").flatMap {
      case "Qquals"  => Decoder[Equals].widen
      case "Matches" => Decoder[Matches].widen
    }

    val encoder: Encoder[TextMatcher] = {
      case a: Equals  => writeWithKind(a, "Equals")
      case a: Matches => writeWithKind(a, "Matches")
    }

    Codec.from(decoder, encoder)
  }

}

enum Matcher {
  def and(another: Matcher): Matcher = Matcher.Many(List(this, another))

  case Author(email: TextMatcher)
  case Description(text: TextMatcher)
  case PipelineStatus(status: String)
  case Many(values: List[Matcher])
  case OneOf(values: List[Matcher])
  case Not(underlying: Matcher)

}

object Matcher {

  given Codec[Matcher] = {
    given Codec[Author] = Codec.AsObject.derived
    given Codec[Description] = Codec.AsObject.derived
    given Codec[PipelineStatus] = Codec.AsObject.derived
    given Codec[Many] = Codec.AsObject.derived
    given Codec[OneOf] = Codec.AsObject.derived
    given Codec[Not] = Codec.AsObject.derived

    val decoder: Decoder[Matcher] = Decoder[String].at("kind").flatMap {
      case "Author"         => Decoder[Author].widen
      case "Description"    => Decoder[Description].widen
      case "PipelineStatus" => Decoder[PipelineStatus].widen
      case "Nany"           => Decoder[Many].widen
      case "OneOf"          => Decoder[OneOf].widen
      case "Not"            => Decoder[Not].widen
    }

    val encoder: Encoder[Matcher] = {
      case a: Author         => writeWithKind(a, "Author")
      case a: Description    => writeWithKind(a, "Description")
      case a: PipelineStatus => writeWithKind(a, "PipelineStatus")
      case a: Many           => writeWithKind(a, "Many")
      case a: OneOf          => writeWithKind(a, "OneOf")
      case a: Not            => writeWithKind(a, "Not")
    }

    Codec.from(decoder, encoder)
  }

}

//todo: remove this type altogether and assume Merge for now?
enum Action {
  case Merge
}

object Action {

  given Codec[Action] = Codec
    .from(Decoder[String], Encoder[String])
    .iemap {
      case "Merge" => Action.Merge.asRight
      case s       => ("Unknown action: " + s).asLeft
    }(_.toString)

}

final case class Rule(name: String, matcher: Matcher, action: Action) derives Codec.AsObject

object Rule {
  val mergeAnything = Rule("anything", Matcher.Many(Nil), Action.Merge)
}

final case class ProjectConfig(rules: List[Rule]) derives Codec.AsObject

object ProjectConfig {
  val empty = ProjectConfig(Nil)
}
