package io.pg.utils

import cats.data.NonEmptyList
import io.circe.Decoder
import cats.implicits._

object CirceUtils {

  def decodeFirstMatch[A](a: Decoder[_ <: A], more: Decoder[_ <: A]*): Decoder[A] =
    NonEmptyList(a.widen[A], more.map(_.widen[A]).toList).reduceK

}
