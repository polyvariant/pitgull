package io.pg.config

import cats.implicits._
import scala.util.matching.Regex
import io.circe.Codec
import io.circe.Decoder
import io.circe.Encoder
import io.circe.DecodingFailure

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
}

import circe.regexCodec

enum TextMatcher {
  case Equals(value: String)
  case Matches(regex: Regex)
}

object TextMatcher {
  given Codec[TextMatcher] = ??? // todo: discriminator: kind
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
  given Codec[Matcher] = ??? // todo: discriminator: kind
}

//todo: remove this type altogether and assume Merge for now?
enum Action {
  case Merge
}

object Action {
  given Codec[Action] = ??? /* deriveEnumerationCodec */
}

final case class Rule(name: String, matcher: Matcher, action: Action) derives Codec.AsObject

object Rule {
  val mergeAnything = Rule("anything", Matcher.Many(Nil), Action.Merge)
}

final case class ProjectConfig(rules: List[Rule]) derives Codec.AsObject

object ProjectConfig {
  val empty = ProjectConfig(Nil)
}
