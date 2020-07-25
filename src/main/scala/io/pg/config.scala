package io.pg

import cats.implicits._

final case class AppConfig(http: HttpConfig, meta: MetaConfig)

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

  val appConfig: ConfigValue[AppConfig] = (httpConfig, metaConfig).parMapN(apply)

}

final case class HttpConfig(port: Int)

final case class MetaConfig(banner: String, version: String, scalaVersion: String)
