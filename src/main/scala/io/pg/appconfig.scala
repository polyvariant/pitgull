package io.pg

import cats.syntax.all._
import ciris.Secret
import sttp.model.Uri

final case class AppConfig(http: HttpConfig, meta: MetaConfig, git: Git, queues: Queues)

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

  private val httpConfig: ConfigValue[HttpConfig] = env("HTTP_PORT").as[Int].default(8080).map(HttpConfig(_))

  private val metaConfig: ConfigValue[MetaConfig] =
    (default(bannerString), default(BuildInfo.version), default(BuildInfo.scalaVersion)).parMapN(MetaConfig)

  val decodeUri: String => ConfigValue[Uri] = s =>
    Uri
      .parse(s)
      .fold(
        e => ConfigValue.failed[Uri](ConfigError(s"Invalid URI, error: $e")),
        ConfigValue.default(_)
      )

  private val gitConfig: ConfigValue[Git] =
    (default(Git.Host.Gitlab), env("GIT_API_URL").flatMap(decodeUri), env("GIT_API_TOKEN").secret).mapN(Git.apply)

  private val queuesConfig: ConfigValue[Queues] = default(100).map(Queues)

  val appConfig: ConfigValue[AppConfig] = (httpConfig, metaConfig, gitConfig, queuesConfig).parMapN(apply)

}

final case class HttpConfig(port: Int)
final case class MetaConfig(banner: String, version: String, scalaVersion: String)

final case class Git(host: Git.Host, apiUrl: Uri, apiToken: Secret[String])

object Git {
  sealed trait Host extends Product with Serializable

  object Host {
    case object Gitlab extends Host
  }

}

final case class Queues(maxSize: Int)
