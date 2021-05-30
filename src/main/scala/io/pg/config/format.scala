package io.pg.config

import cats.implicits._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.ConfiguredJsonCodec
import io.circe.generic.extras.semiauto._
import scala.util.matching.Regex
import io.circe.Codec
import io.circe.Decoder
import io.circe.Encoder
import io.circe.DecodingFailure

object circe {
  implicit val circeConfig: Configuration =
    Configuration.default.withDiscriminator("kind").withSnakeCaseConstructorNames

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

import circe.circeConfig
import circe.regexCodec

sealed trait TextMatcher extends Product with Serializable

object TextMatcher {
  final case class Equals(value: String) extends TextMatcher
  final case class Matches(regex: Regex) extends TextMatcher
  implicit val codec: Codec[TextMatcher] = deriveConfiguredCodec
}

@ConfiguredJsonCodec()
sealed trait Matcher extends Product with Serializable {
  def and(another: Matcher): Matcher = Matcher.Many(List(this, another))
}

object Matcher {
  final case class Author(email: TextMatcher) extends Matcher
  final case class Description(text: TextMatcher) extends Matcher
  final case class PipelineStatus(status: String) extends Matcher
  final case class Many(values: List[Matcher]) extends Matcher
  final case class OneOf(values: List[Matcher]) extends Matcher
  final case class Not(underlying: Matcher) extends Matcher
}

//todo: remove this type altogether and assume Merge for now?
sealed trait Action extends Product with Serializable

object Action {
  case object Merge extends Action

  implicit val codec: Codec[Action] = deriveEnumerationCodec
}

@ConfiguredJsonCodec()
final case class Rule(name: String, matcher: Matcher, action: Action)

object Rule {
  val mergeAnything = Matcher.Many(Nil)
}

@ConfiguredJsonCodec()
final case class ProjectConfig(rules: List[Rule])

object ProjectConfig {
  val empty = ProjectConfig(Nil)
}
