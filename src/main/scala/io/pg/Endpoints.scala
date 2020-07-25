package io.pg

import io.circe.generic.extras.ConfiguredJsonCodec
import io.pg.CirceConfig._

object Endpoints {
  import sttp.tapir._
  import sttp.tapir.json.circe._

  private val base = infallibleEndpoint.in("api" / "v1")

  val hello: Endpoint[String, Nothing, Hello, Nothing] = base.in("hello" / path[String]("name")).out(jsonBody[Hello])

}

@ConfiguredJsonCodec
final case class Hello(message: String)
