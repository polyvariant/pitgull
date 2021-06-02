package io.pg

import cats.syntax.all._
import ciris.Secret
import org.http4s.Headers
import sttp.model.Uri
import org.typelevel.ci.CIString

final case class AppConfig(
  http: HttpConfig,
  meta: MetaConfig,
  git: Git,
  queues: Queues,
  middleware: MiddlewareConfig
)

object AppConfig {

  private val bannerString =
    """|        _ _              _ _
       |       (_) |            | | |
       |  _ __  _| |_ __ _ _   _| | |
       | | '_ \| | __/ _` | | | | | |
       | | |_) | | || (_| | |_| | | |
       | | .__/|_|\__\__, |\__,_|_|_|
       | | |          __/ |
       | |_|         |___/
       |
       |""".stripMargin

  import ciris._

  val httpConfig: ConfigValue[ciris.Effect, HttpConfig] =
    env("HTTP_PORT").as[Int].default(8080).map(HttpConfig(_))

  val metaConfig: ConfigValue[ciris.Effect, MetaConfig] =
    (
      default(bannerString),
      default(BuildInfo.version),
      default(BuildInfo.scalaVersion)
    ).parMapN(MetaConfig)

  implicit val decodeUri: ConfigDecoder[String, Uri] =
    ConfigDecoder[String, String].mapEither { (key, value) =>
      Uri
        .parse(value)
        .leftMap(e => ConfigError(s"Invalid URI ($value at $key), error: $e"))
    }

  val gitConfig: ConfigValue[ciris.Effect, Git] =
    (
      default(Git.Host.Gitlab),
      env("GIT_API_URL").as[Uri],
      env("GIT_API_TOKEN").secret
    ).mapN(Git.apply)

  private val queuesConfig: ConfigValue[ciris.Effect, Queues] = default(100).map(Queues)

  private val middlewareConfig: ConfigValue[ciris.Effect, MiddlewareConfig] =
    default(Headers.SensitiveHeaders + CIString("Private-Token")).map(MiddlewareConfig)

  val appConfig: ConfigValue[ciris.Effect, AppConfig] =
    (httpConfig, metaConfig, gitConfig, queuesConfig, middlewareConfig).parMapN(apply)

}

final case class HttpConfig(port: Int)

final case class MetaConfig(
  banner: String,
  version: String,
  scalaVersion: String
)

final case class Git(host: Git.Host, apiUrl: Uri, apiToken: Secret[String])

object Git {
  sealed trait Host extends Product with Serializable

  object Host {
    case object Gitlab extends Host
  }

}

final case class Queues(maxSize: Int)

final case class MiddlewareConfig(sensitiveHeaders: Set[CIString])
