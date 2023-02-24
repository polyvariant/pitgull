package org.polyvariant

import cats.implicits.*
import cats.MonadThrow
import sttp.model.Uri
import scala.util.Try

final case class Config(
  gitlabUri: Uri,
  token: String,
  project: Long,
  botUser: String,
  pitgullWebhookUrl: Uri
)

object Config {

  def fromArgs[F[_]: MonadThrow](
    args: Map[String, String]
  ): F[Config] =
    MonadThrow[F]
      .catchNonFatal {
        Config(
          Uri.unsafeParse(args("url")),
          args("token"),
          args("project").toLong,
          args("bot"),
          Uri.unsafeParse(args("webhook"))
        )
      }
      .recoverWith { case _ =>
        MonadThrow[F].raiseError(ArgumentsParsingException)
      }

  val usage = """
  |This program prepares your gitlab project for integration with Pitgull
  |by deleting existing Scala Steward mere requests and setting up
  |a webhook for triggering Pitgull.
  |
  |CLI Arguments:
  | --url - your gitlab url like https://gitlab.com/
  | --token - your gitlab personal token, needs to have full access to project
  | --project - project ID, can be found on project main page
  | --bot - user name of Scala Steward bot user
  | --webhook - Pitgull target url like https://pitgull.example.com/webhook
  """.stripMargin

  case object ArgumentsParsingException extends Exception("Failed to parse CLI arguments")
}
