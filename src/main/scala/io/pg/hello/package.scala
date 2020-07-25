package io.pg.hello

import cats.tagless.finalAlg
import cats.data.NonEmptyList
import sttp.tapir.server.ServerEndpoint
import io.pg.Endpoints
import io.pg.Hello
import cats.implicits._

@finalAlg
trait HelloService[F[_]] {

  def hello(name: String): F[Hello]

}

object HelloService {

  def instance[F[_]: Applicative]: HelloService[F] = s => Hello(s).pure[F]

}

object HelloRouter {

  def routes[F[_]: HelloService]: NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] =
    NonEmptyList.of(
      Endpoints.hello.serverLogicRecoverErrors(HelloService[F].hello)
    )

}
