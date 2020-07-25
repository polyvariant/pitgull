package io.pg

import io.circe.generic.extras.ConfiguredJsonCodec
import io.circe.generic.extras.Configuration

object Endpoints {
  import sttp.tapir._
  import sttp.tapir.json.circe._

  private val base = infallibleEndpoint.in("api" / "v1")

  val hello: Endpoint[String, Nothing, Hello, Nothing] = base.in("hello" / path[String]("name")).out(jsonBody[Hello])

}

import CirceConfig._

@ConfiguredJsonCodec
final case class Hello(message: String)

object CirceConfig {

  implicit val config: Configuration = Configuration.default

}
