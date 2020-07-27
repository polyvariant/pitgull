package io.pg

import cats.implicits._
import io.circe.generic.extras.Configuration
import ciris.Secret

final case class AppConfig(http: HttpConfig, meta: MetaConfig, git: Git)

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

  private val gitConfig: ConfigValue[Git] = (default(Git.Host.Gitlab), env("GIT_API_URL"), env("GIT_API_TOKEN").secret).mapN(Git.apply)

  val appConfig: ConfigValue[AppConfig] = (httpConfig, metaConfig, gitConfig).parMapN(apply)

}

final case class HttpConfig(port: Int)
final case class MetaConfig(banner: String, version: String, scalaVersion: String)

final case class Git(host: Git.Host, apiUrl: String, apiToken: Secret[String])

object Git {
  sealed trait Host extends Product with Serializable

  object Host {
    case object Gitlab extends Host
  }

}

object CirceConfig {
  implicit val config: Configuration = Configuration.default
}
