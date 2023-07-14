package io.pg.config

import io.circe.Codec
import io.circe.Decoder
import io.circe.DecodingFailure
import io.circe.Json
import scala.deriving.Mirror

// Temporary replacement for https://github.com/circe/circe/pull/1800
object DiscriminatedCodec {

  import scala.deriving._
  import scala.compiletime._

  private inline def deriveAll[T <: Tuple]: List[Codec.AsObject[_]] = inline erasedValue[T] match {
    case _: EmptyTuple => Nil
    case _: (h *: t)   =>
      Codec
        .AsObject
        .derived[h](
          // feels odd but works
          using summonInline[Mirror.Of[h]]
        ) :: deriveAll[t]
  }

  inline def derived[A](
    discriminator: String
  )(
    using inline m: Mirror.SumOf[A]
  ): Codec.AsObject[A] = {

    val codecs: List[Codec.AsObject[A]] = deriveAll[m.MirroredElemTypes].map(_.asInstanceOf[Codec.AsObject[A]])

    val labels =
      summonAll[Tuple.Map[m.MirroredElemLabels, ValueOf]]
        .toList
        .asInstanceOf[List[ValueOf[String]]]
        .map(_.value)

    Codec
      .AsObject
      .from[A](
        Decoder[String].at(discriminator).flatMap { key =>
          val index = labels.indexOf(key)

          if (index < 0) Decoder.failedWithMessage(s"Unknown discriminator field $discriminator: $key")
          else codecs(index)
        },
        value => {
          val index = m.ordinal(value)

          codecs(index)
            .mapJsonObject(_.add(discriminator, Json.fromString(labels(index))))
            .encodeObject(value)
        }
      )
  }

}
