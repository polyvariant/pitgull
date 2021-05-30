package io.pg

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

import cats.Parallel
import cats.effect.ConcurrentEffect
import cats.effect.ContextShift
import cats.effect.Effect
import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import cats.effect.Resource
import cats.effect.Sync
import cats.effect.Timer
import cats.effect.implicits._
import cats.syntax.all._
import io.chrisdavenport.cats.time.instances.all._
import io.odin.Level
import io.odin.Logger
import io.odin.formatter.Formatter
import io.pg.Prelude._
import org.http4s.HttpApp
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware
import io.pg.config.ProjectConfigReader
import cats.effect.Blocker
import io.pg.gitlab.webhook.Project
import org.http4s.client.blaze.BlazeClientBuilder
import java.nio.file.Paths
import org.dhallj.core.Expr
import org.dhallj.codec.Encoder
import org.dhallj.codec.Decoder
import java.util.Map.Entry
import org.dhallj.parser.DhallParser

object Main extends IOApp {

  def mkLogger[F[_]: ConcurrentEffect: Timer: ContextShift]: Resource[F, Logger[F]] = {

    val console = io.odin.consoleLogger[F](formatter = Formatter.colorful).withMinimalLevel(Level.Info).pure[Resource[F, *]]

    val file = io
      .odin
      .asyncRollingFileLogger[F](
        fileNamePattern = dateTime => show"/tmp/log/pitgull/pitgull-logs-${dateTime.toLocalDate}.txt",
        rolloverInterval = 1.day.some,
        maxFileSizeInBytes = (10L * 1024 * 1024 /* 10MB */ ).some,
        maxBufferSize = 10.some,
        formatter = Formatter.colorful,
        minLevel = Level.Debug
      )

    console |+| file
  }
    .evalTap { logger =>
      Sync[F].delay(OdinInterop.globalLogger.set(logger.mapK(Effect.toIOK).some))
    }

  def mkServer[F[_]: Logger: ConcurrentEffect: Timer](
    config: AppConfig,
    routes: HttpApp[F]
  ) = {
    val app = middleware
      .Logger
      .httpApp(
        logHeaders = true,
        logBody = true,
        logAction = (Logger[F].debug(_: String)).some
      )(routes)

    BlazeServerBuilder[F](ExecutionContext.global)
      .withHttpApp(app)
      .bindHttp(port = config.http.port, host = "0.0.0.0")
      .withBanner(config.meta.banner.linesIterator.toList)
      .resource
  }

  def logStarting[F[_]: Logger](meta: MetaConfig) =
    Logger[F].info("Starting application", Map("version" -> meta.version, "scalaVersion" -> meta.scalaVersion))

  def logStarted[F[_]: Logger](meta: MetaConfig) =
    Logger[F].info("Started application", Map("version" -> meta.version, "scalaVersion" -> meta.scalaVersion))

  def serve[F[_]: ConcurrentEffect: ContextShift: Timer: Parallel](config: AppConfig) =
    for {
      implicit0(logger: Logger[F]) <- mkLogger[F]
      _                            <- logStarting(config.meta).resource_
      resources                    <- Application.resource[F](config)
      _                            <- mkServer[F](config, resources.routes)
      _                            <- resources.background.parTraverse_(_.run).background
      _                            <- logStarted(config.meta).resource_
    } yield ()

  def run(args: List[String]): IO[ExitCode] =
    AppConfig
      .appConfig
      .resource[IO]
      .flatMap(serve[IO])
      .use(_ => IO.never)

}

object Demo extends IOApp.Simple {

  def run: IO[Unit] = Blocker[IO].use { blocker =>
    BlazeClientBuilder[IO](executionContext).resource.use { implicit c =>
      import org.dhallj.syntax._
      import org.dhallj.imports.syntax._
      import org.dhallj.codec.syntax._

      import scala.jdk.CollectionConverters._

      def mapToEntryArray(map: Map[String, Expr]): Array[Entry[String, Expr]] = map.asJava.entrySet().asScala.toArray
      def mapToRecord(map: Map[String, Expr]): Expr = Expr.makeRecordLiteral(mapToEntryArray(map))

      sealed trait MergeRequestStatus extends Product with Serializable
      object MergeRequestStatus {
        case object Success extends MergeRequestStatus
        case class Other(s: String) extends MergeRequestStatus
        val dhallType: Expr = Expr.makeUnionType(
          mapToEntryArray(
            Map("Success" -> mapToRecord(Map.empty), "Other" -> mapToRecord(Map("s" -> Expr.Constants.TEXT)))
          )
        )

        implicit val encoder: Encoder[MergeRequestStatus] = new Encoder[MergeRequestStatus] {
          def encode(value: MergeRequestStatus, target: Option[Expr]): Expr = mapToRecord {
            value match {
              case Success  => Expr.record
              case Other(s) => ???
            }
          }

          def dhallType(value: Option[MergeRequestStatus], target: Option[Expr]): Expr = MergeRequestStatus.dhallType

        }
      }

      case class MergeRequestInfo(status: MergeRequestStatus, authorUsername: String, description: String)

      implicit val e: Encoder[MergeRequestInfo] = new Encoder[MergeRequestInfo] {
        def encode(value: MergeRequestInfo, target: Option[Expr]): Expr = mapToRecord(
          Map("status" -> value.status.asExpr)
        )

        def dhallType(value: Option[MergeRequestInfo], target: Option[Expr]): Expr = mapToRecord(
          Map("status" -> MergeRequestStatus.dhallType, "authorUsername" -> Expr.Constants.TEXT, "description" -> Expr.Constants.TEXT)
        )

      }

      case class Mismatch(message: String)
      sealed trait Matched extends Product with Serializable
      object Matched {
        case object Ok extends Matched
        case class NotOk(errors: List[Mismatch]) extends Matched
      }
      implicit val d: Decoder[Matched] = ???

      fs2
        .io
        .file
        .readAll[IO](Paths.get("./example2.dhall"), blocker, 4096)
        .through(fs2.text.utf8Decode[IO])
        .compile
        .string
        .flatMap(_.parseExpr.liftTo[IO])
        .flatMap(_.resolveImports[IO])
        .flatMap(_.typeCheck.liftTo[IO])
        .map(_.normalize)
        .map(_.as[MergeRequestInfo => Matched])
        .flatMap(a => IO(println(a)))
    }
  }

}
