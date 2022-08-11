package io.pg.config

import cats.implicits._
import scala.util.matching.Regex
import io.circe.Codec
import io.circe.Decoder
import io.circe.Encoder
import io.circe.DecodingFailure
import io.circe.Json

object circe {

  private val decodeRegex: Decoder[Regex] = Decoder[String].flatMap { s =>
    Either
      .catchNonFatal(s.r)
      .leftMap(DecodingFailure.fromThrowable(_, Nil))
      .liftTo[Decoder]
  }

  private val encodeRegex: Encoder[Regex] = Encoder.encodeString.contramap[Regex](_.toString)

  implicit val regexCodec: Codec[Regex] = Codec.from(decodeRegex, encodeRegex)
}

import circe.regexCodec

enum TextMatcher {
  case Equals(value: String)
  case Matches(regex: Regex)

  override def equals(another: Any) = (this, another) match {
    // Regex uses reference equality by default.
    // By using `.regex` we convert it back to a pattern string for better comparison.
    case (Matches(p1), Matches(p2)) => p1.regex == p2.regex
    case (Equals(e1), Equals(e2))   => e1 == e2
    case _                          => false
  }

}

object TextMatcher {
  given Codec[TextMatcher] = DiscriminatedCodec.derived("kind")
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
  given Codec[Matcher] = DiscriminatedCodec.derived("kind")
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
