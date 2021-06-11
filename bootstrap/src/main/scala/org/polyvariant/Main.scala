package org.polyvariant

import cats.implicits.*
import cats.effect.*

import sttp.model.Uri

import sttp.client3.*
import org.polyvariant.Gitlab.MergeRequestInfo
import cats.Applicative
import sttp.monad.MonadError
import cats.MonadThrow

object Main extends IOApp {

  private def printMergeRequests[F[_]: Logger: Applicative](mergeRequests: List[MergeRequestInfo]): F[Unit] = 
    mergeRequests.traverse { mr =>
      Logger[F].info(s"ID: ${mr.mergeRequestIid} by: ${mr.authorUsername}")
    }.void

  private def readConsent[F[_]: Console: Applicative]: F[Boolean] = 
    Console[F].readChar().map(_.toString.toLowerCase == "y")

  private def qualifyMergeRequestsForDeletion(botUserName: String, mergeRequests: List[MergeRequestInfo]): List[MergeRequestInfo] = 
    mergeRequests.filter(_.authorUsername == botUserName)

  private def program[F[_]: Logger: Console: Async: MonadThrow](args: List[String]): F[Unit] =
    given backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
    for {
      _            <- Logger[F].info("Starting pitgull bootstrap!")
      parsedArgs = Args.parse(args)
      config = Config.fromArgs(parsedArgs)
      gitlab = Gitlab.sttpInstance[F](config.gitlabUri, config.token)
      mrs          <- gitlab.mergeRequests(config.project)
      _            <- Logger[F].info(s"Merge requests found: ${mrs.length}")
      _            <- printMergeRequests(mrs)
      botMrs = qualifyMergeRequestsForDeletion(config.botUser, mrs)
      _            <- Logger[F].info(s"Will delete merge requests: ${botMrs.map(_.mergeRequestIid)}")
      _            <- Logger[F].info("Do you want to proceed? y/Y")
      _            <- MonadThrow[F].ifM(readConsent)(ifTrue = MonadThrow[F].pure(()), ifFalse = MonadThrow[F].raiseError(new Exception("User rejected deletion")))
      _            <- botMrs.traverse(mr => gitlab.deleteMergeRequest(config.project, mr.mergeRequestIid))
      _            <- Logger[F].info("Done processing merge requests")
      _            <- Logger[F].info("Creating webhook")
      _            <- gitlab.createWebhook(config.project, config.pitgullWebhookUrl)
      _            <- Logger[F].info("Webhook created")
      _            <- Logger[F].success("Bootstrap finished")
    } yield ()

  override def run(args: List[String]): IO[ExitCode] = {
    given Logger[IO] = Logger.wrappedPrint[IO]
    given Console[IO] = Console.instance[IO]
    program[IO](args) *>
      IO.pure(ExitCode.Success)
  }

  final case class Config(
    gitlabUri: Uri,
    token: String,
    project: Long,
    botUser: String,
    pitgullWebhookUrl: Uri
  )
  object Config {
    def fromArgs(args: Map[String, String]): Config = // FIXME: this is unsafe
      Config(Uri.unsafeParse(args("url")), args("token"), args("project").toLong, args("bot"), Uri.unsafeParse(args("webhook")))
  }
}
