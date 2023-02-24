package io.pg

object TextUtils {

  def trim(
    maxChars: Int
  )(
    s: String
  ): String = {
    val ellipsis = "." * 3
    if (s.lengthIs > maxChars) s.take(maxChars - ellipsis.length) ++ ellipsis
    else s
  }

  def inline(
    s: String
  ): String =
    s.replaceAll("\n", " ")

}
