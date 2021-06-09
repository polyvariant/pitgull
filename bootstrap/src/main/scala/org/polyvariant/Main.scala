package org.polyvariant

import cats.implicits.*
import cats.effect.*

import sttp.model.Uri

import sttp.client3.*
import org.polyvariant.Gitlab.MergeRequestInfo
import cats.Applicative
import sttp.monad.MonadError

object Main extends IOApp {

  private def printMergeRequests[F[_]: Logger: Applicative](mergeRequests: List[MergeRequestInfo]): F[Unit] = 
    mergeRequests.traverse { mr =>
      Logger[F].info(s"ID: ${mr.mergeRequestIid} by: ${mr.authorUsername}")
    }.void

  private def qualifyMergeRequests(botUserName: String, mergeRequests: List[MergeRequestInfo]): List[MergeRequestInfo] = 
    mergeRequests.filter(_.authorUsername == botUserName)

  private def program[F[_]: Logger: Async](args: List[String]): F[Unit] =
    given backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
    for {
      _   <- Logger[F].info("Starting pitgull bootstrap!")
      parsedArgs = Args.parse(args)
      config = Config.fromArgs(parsedArgs)
      gitlab = Gitlab.sttpInstance[F](config.gitlabUri, config.token)
      mrs <- gitlab.mergeRequests(config.project)
      _   <- Logger[F].info(s"Merge requests found: ${mrs.length}")
      _   <- printMergeRequests(mrs)
      botMrs = qualifyMergeRequests(config.botUser, mrs)
      _   <- Logger[F].info(s"Will delete merge requests: ${botMrs.map(_.mergeRequestIid)}")
      _   <- botMrs.traverse(mr => gitlab.deleteMergeRequest(config.project, mr.mergeRequestIid))
      _   <- Logger[F].info("Done processing merge requests")
    } yield ()

  override def run(args: List[String]): IO[ExitCode] = {
    given logger: Logger[IO] = Logger.wrappedPrint[IO]
    program[IO](args) *>
      IO.pure(ExitCode.Success)
  }

  final case class Config(
    gitlabUri: Uri,
    token: String,
    project: Long,
    botUser: String
  )
  object Config {
    def fromArgs(args: Map[String, String]): Config = // FIXME: this is unsafe
      Config(Uri.unsafeParse(args("url")), args("token"), args("project").toLong, args("bot"))
  }
}
