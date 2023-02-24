package org.polyvariant

object Args {
  private val switch = "-(\\w+)".r
  private val option = "--(\\w+)".r

  private def parseNext(
    pendingArguments: List[String],
    previousResult: Map[String, String]
  ): Map[String, String] =
    pendingArguments match {
      case Nil                          => previousResult
      case option(opt) :: value :: tail => parseNext(tail, previousResult ++ Map(opt -> value))
      case switch(opt) :: tail          => parseNext(tail, previousResult ++ Map(opt -> null))
      case text :: Nil                  => previousResult ++ Map(text -> null)
      case text :: tail                 => parseNext(tail, previousResult ++ Map(text -> null))
    }

  // TODO: Consider switching to https://ben.kirw.in/decline/ after https://github.com/bkirwi/decline/pull/293
  def parse(
    args: List[String]
  ): Map[String, String] =
    parseNext(args.toList, Map())

}
