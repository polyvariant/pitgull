package org.polyvariant

import cats.implicits.*
import cats.effect.*

import sttp.model.Uri

import sttp.client3.*
import org.polyvariant.Gitlab.MergeRequestInfo
import cats.Applicative

object Main extends IOApp {


  private def printMergeRequests[F[_]: Logger: Applicative](mergeRequests: List[MergeRequestInfo]): F[Unit] = 
    mergeRequests.traverse { mr =>
      Logger[F].info(s"[${mr.mergeRequestIid}] ${mr.description.getOrElse("Missing description")}")
    }.void


  private def program[F[_]: Logger: Async](args: List[String]): F[Unit] =
    given backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
    for {
      _   <- Logger[F].info("Starting pitgull bootstrap!")
      parsedArgs = Args.parse(args)
      gitlabUri = Uri.unsafeParse(parsedArgs("url"))
      token = parsedArgs("token")
      project = parsedArgs("project")
      gitlab = Gitlab.sttpInstance[F](gitlabUri, token)
      mrs <- gitlab.mergeRequests(project.toLong)
      _   <- Logger[F].info(s"Merge requests found:")
      _   <- printMergeRequests(mrs)
    } yield ()

  override def run(args: List[String]): IO[ExitCode] =
    given logger: Logger[IO] = Logger.wrappedPrint[IO]
    program[IO](args) *>
      IO.pure(ExitCode.Success)

}
