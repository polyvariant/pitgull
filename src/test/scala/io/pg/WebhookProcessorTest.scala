package io.pg

import cats.effect.IO
import cats.effect.Resource
import cats.effect.Sync
import cats.effect.concurrent.Ref
import io.pg.Prelude._
import io.pg.config.ProjectConfig
import io.pg.config.ProjectConfigReader
import io.pg.fakes.ProjectActionsStateFake
import io.pg.fakes.ProjectConfigReaderFake
import io.pg.gitlab.webhook.Project
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.messaging.Processor
import io.pg.webhook.WebhookProcessor
import monocle.Lens
import weaver.SimpleIOSuite
import weaver.Expectations

object WebhookProcessorTest extends SimpleIOSuite {

  final case class Resources[F[_]](
    actions: ProjectActions[F],
    resolver: StateResolver[F],
    projectModifiers: ProjectActionsStateFake.State.Modifiers[F],
    projectConfigs: ProjectConfigReader[F],
    projectConfigModifiers: ProjectConfigReaderFake.State.Modifiers[F],
    process: Processor[IO, WebhookEvent]
  )

  val mkResources =
    ProjectConfigReaderFake
      .refInstance[IO]
      .flatMap { implicit configReader =>
        ProjectActionsStateFake.refInstance[IO].map { implicit projects =>
          implicit val logger = io.odin.consoleLogger[IO]()

          Resources(
            actions = projects,
            resolver = projects,
            projectModifiers = projects,
            projectConfigs = configReader,
            projectConfigModifiers = configReader,
            process = WebhookProcessor.instance[IO]
          )
        }
      }
      .resource

  def testWithResources(name: String)(use: Resources[IO] => IO[Expectations]) =
    test(name)(mkResources.use(use))

  testWithResources("known project with no MRs") { resources =>
    import resources._
    val projectId = 66L
    val projectName = "kubukoz/demo"

    projectConfigModifiers.register(projectId, ProjectConfig.empty) *>
      process.processOne(WebhookEvent(Project(projectId, projectName), "merge_request")).compile.drain.as(success)
  }
}
