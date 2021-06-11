package org.polyvariant

import cats.implicits.*
import cats.effect.*

import sttp.model.Uri

import sttp.client3.*
import org.polyvariant.Gitlab.MergeRequestInfo
import cats.Applicative
import sttp.monad.MonadError
import cats.MonadThrow
import org.polyvariant.Config.ArgumentsParsingException

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
    val parsedArgs = Args.parse(args)
    for {
      config       <- Config.fromArgs(parsedArgs)
      _            <- Logger[F].info("Starting pitgull bootstrap!")
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
    program[IO](args).recoverWith{
      case Config.ArgumentsParsingException => 
        Logger[IO].info(Config.usage)
      case e: Exception => 
        Logger[IO].error(s"Unexpected error ocurred: $e")
    } *>
      IO.pure(ExitCode.Success)
  }

}
