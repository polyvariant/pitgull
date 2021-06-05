package org.polyvariant

import cats.implicits.*
import cats.effect.*

object Main extends IOApp {

  private def program[F[_]: Logger: Sync](args: List[String]): F[Unit] = 
    Logger[F].info("Starting pitgull bootstrap!") *>
     Logger[F].debug(Args.parse(args).toString)

  override def run(args: List[String]): IO[ExitCode] = {
    given logger: Logger[IO] = Logger.wrappedPrint[IO]
    program[IO](args) *> 
      IO.pure(ExitCode.Success)
  }
     
     
}
