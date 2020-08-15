package io.pg.config

import io.circe.generic.JsonCodec
import io.circe.Decoder
import io.pg.utils.CirceUtils._

import io.circe.generic.semiauto._

sealed trait TextMatcher extends Product with Serializable

object TextMatcher {
  final case class Equals(value: String) extends TextMatcher
  final case class Matches(regex: String) extends TextMatcher

  implicit val decoder: Decoder[TextMatcher] = decodeFirstMatch(
    deriveDecoder[Equals],
    deriveDecoder[Matches]
  )

}

sealed trait Match extends Product with Serializable

object Match {
  final case class Author(email: TextMatcher) extends Match
  final case class Description(text: TextMatcher) extends Match
  final case class PipelineStatus(status: String) extends Match

  implicit val decoder: Decoder[Match] = decodeFirstMatch(
    deriveDecoder[Author],
    deriveDecoder[Description],
    deriveDecoder[PipelineStatus]
  )

}

@JsonCodec(decodeOnly = true)
final case class Rule(name: String, matches: List[Match])

@JsonCodec(decodeOnly = true)
final case class ProjectConfig(rules: List[Rule])
