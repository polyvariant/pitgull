package org.polyvariant

import cats.implicits.*
import cats.effect.*

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    IO.println("Hello World!") *>
     IO.println(Args.parse(args).toString) *> 
     IO.pure(ExitCode.Success)
}
