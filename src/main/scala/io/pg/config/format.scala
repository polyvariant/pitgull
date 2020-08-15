package io.pg.config

import cats.implicits._
import io.circe.generic.JsonCodec
import io.circe.Decoder
import cats.data.NonEmptyList

import io.circe.generic.semiauto._

sealed trait TextMatcher extends Product with Serializable

object TextMatcher {
  final case class Equals(value: String) extends TextMatcher
  final case class Matches(regex: String) extends TextMatcher

  implicit val decoder: Decoder[TextMatcher] = NonEmptyList
    .of[Decoder[TextMatcher]](
      deriveDecoder[Equals].widen,
      deriveDecoder[Matches].widen
    )
    .reduceK

}

sealed trait Match extends Product with Serializable

object Match {
  final case class Author(email: TextMatcher) extends Match
  final case class Description(text: TextMatcher) extends Match
  final case class PipelineStatus(status: String) extends Match

  private def firstMatching[A](a: Decoder[_ <: A], more: Decoder[_ <: A]*): Decoder[A] =
    NonEmptyList(a.widen[A], more.map(_.widen[A]).toList).reduceK

  implicit val decoder: Decoder[Match] = firstMatching(
    deriveDecoder[Author],
    deriveDecoder[Description],
    deriveDecoder[PipelineStatus]
  )

}

@JsonCodec(decodeOnly = true)
final case class Rule(name: String, matches: List[Match])

@JsonCodec(decodeOnly = true)
final case class ProjectConfig(rules: List[Rule])
