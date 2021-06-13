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
import cats.effect.std.Console
import cats.Monad

object Main extends IOApp {

  private def printMergeRequests[F[_]: Logger: Applicative](mergeRequests: List[MergeRequestInfo]): F[Unit] =
    mergeRequests.traverse { mr =>
      Logger[F].info(s"ID: ${mr.mergeRequestIid} by: ${mr.authorUsername}")
    }.void

  private def readConsent[F[_]: Console: MonadThrow]: F[Unit] =
    MonadThrow[F]
      .ifM(Console[F].readLine.map(_.trim.toLowerCase == "y"))(
        ifTrue = MonadThrow[F].pure(()),
        ifFalse = MonadThrow[F].raiseError(new Exception("User rejected deletion"))
      )

  private def qualifyMergeRequestsForDeletion(botUserName: String, mergeRequests: List[MergeRequestInfo]): List[MergeRequestInfo] =
    mergeRequests.filter(_.authorUsername == botUserName)

  private def deleteMergeRequests[F[_]: Gitlab: Logger: Applicative](project: Long, mergeRequests: List[MergeRequestInfo]): F[Unit] =
    mergeRequests.traverse(mr => Gitlab[F].deleteMergeRequest(project, mr.mergeRequestIid)).void

  private def createWebhook[F[_]: Gitlab: Logger: Applicative](project: Long, webhook: Uri): F[Unit] =
    Logger[F].info("Creating webhook") *>
      Gitlab[F].createWebhook(project, webhook) *>
      Logger[F].info("Webhook created")

  private def configureWebhooks[F[_]: Gitlab: Logger: Monad](project: Long, webhook: Uri): F[Unit] = for {
    hooks <- Gitlab[F].listWebhooks(project).map(_.filter(_.url == webhook.toString))
    _     <- Monad[F]
               .ifM(hooks.nonEmpty.pure[F])(
                 ifTrue = Logger[F].success("Webhook already exists"),
                 ifFalse = createWebhook(project, webhook)
               )
  } yield ()

  private def program[F[_]: Logger: Console: Async](args: List[String]): F[Unit] = {
    given SttpBackend[Identity, Any] = HttpURLConnectionBackend()
    val parsedArgs = Args.parse(args)
    for {
      config <- Config.fromArgs(parsedArgs)
      _      <- Logger[F].info("Starting pitgull bootstrap!")
      given Gitlab[F] = Gitlab.sttpInstance[F](config.gitlabUri, config.token)
      mrs    <- Gitlab[F].mergeRequests(config.project)
      _      <- Logger[F].info(s"Merge requests found: ${mrs.length}")
      _      <- printMergeRequests(mrs)
      botMrs = qualifyMergeRequestsForDeletion(config.botUser, mrs)
      _      <- Logger[F].info(s"Will delete merge requests: ${botMrs.map(_.mergeRequestIid).mkString(", ")}")
      _      <- Logger[F].info("Do you want to proceed? y/Y")
      _      <- readConsent
      _      <- deleteMergeRequests(config.project, botMrs)
      _      <- Logger[F].info("Done processing merge requests")
      _      <- Logger[F].info("Configuring webhook")
      _      <- configureWebhooks(config.project, config.pitgullWebhookUrl)
      _      <- Logger[F].success("Bootstrap finished")
    } yield ()
  }

  override def run(args: List[String]): IO[ExitCode] = {
    given Logger[IO] = Logger.wrappedPrint[IO]
    program[IO](args).recoverWith {
      case Config.ArgumentsParsingException =>
        Logger[IO].info(Config.usage)
      case e: Exception                     =>
        Logger[IO].error(s"Unexpected error ocurred: $e")
    } *>
      IO.pure(ExitCode.Success)
  }

}
