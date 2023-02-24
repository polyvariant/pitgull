package io.pg

import cats.effect.IO
import cats.implicits._
import cats.effect.implicits._
import io.pg.config.ProjectConfig
import io.pg.config.ProjectConfigReader
import io.pg.fakes.ProjectActionsStateFake
import io.pg.fakes.ProjectConfigReaderFake
import io.pg.gitlab.webhook.Project
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.webhook.WebhookProcessor
import weaver.Expectations
import weaver.SimpleIOSuite
import io.pg.config.Rule
import io.pg.config.Matcher
import io.pg.config.Action
import io.pg.config.TextMatcher
import io.pg.MergeRequestState.Mergeability
import io.odin.Logger

object WebhookProcessorTest extends SimpleIOSuite {

  final case class Resources[F[_]](
    actions: ProjectActions[F],
    resolver: StateResolver[F],
    projectModifiers: ProjectActionsStateFake.State.Modifiers[F],
    projectConfigs: ProjectConfigReader[F],
    projectConfigModifiers: ProjectConfigReaderFake.State.Modifiers[F],
    process: WebhookEvent => F[Unit]
  )

  val mkResources =
    ProjectConfigReaderFake
      .refInstance[IO]
      .flatMap { implicit configReader =>
        given Logger[IO] = io.odin.consoleLogger[IO]()

        ProjectActionsStateFake.refInstance[IO].map { implicit projects =>
          implicit val mergeRequests: MergeRequests[IO] = MergeRequests.instance[IO]

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
      .toResource

  def testWithResources(
    name: String
  )(
    use: Resources[IO] => IO[Expectations]
  ) =
    test(name)(mkResources.use(use))

  /*
  testWithResources("unknown project") { resources =>
    import resources._
    val projectId = 66L

    process(WebhookEvent(Project(projectId), "merge_request"))
      .attempt
      .map { result =>
        expect(result.isLeft)
      }
  }

  testWithResources("known project with no MRs") { resources =>
    import resources._
    val projectId = 66L

    projectConfigModifiers.register(projectId, ProjectConfig.empty) *>
      process(WebhookEvent(Project(projectId), "merge_request")).as(success)
  }

  testWithResources("known project with one mergeable MR and one non-mergeable MR") { resources =>
    import resources._
    val projectId = 66L

    val project = Project(projectId)

    val matchSuccessfulPipeline =
      Rule("pipeline successful", Matcher.PipelineStatus("success"), Action.Merge)

    for {
      _              <- projectConfigModifiers.register(projectId, ProjectConfig(List(matchSuccessfulPipeline)))
      mergeRequestId <- projectModifiers.open(projectId, "anyone@example.com", None)
      _              <- projectModifiers.finishPipeline(projectId, mergeRequestId)
      freshMR        <- projectModifiers.open(projectId, "anyone@example.com", None)

      _                         <- process(WebhookEvent(project, "merge_request"))
      mergeRequestsAfterProcess <- resolver.resolve(project)
    } yield expect {
      mergeRequestsAfterProcess.map(_.mergeRequestIid) == List(freshMR)
    }
  }
   */
  testWithResources("known project with one mergeable MR and one rebaseable MR") { resources =>
    import resources._
    val projectId = 66L

    val project = Project(projectId)

    val perform = (process(WebhookEvent(project, "merge_request")) *> resolver.resolve(project), projectModifiers.getActionLog).tupled

    for {
      _   <- projectConfigModifiers.register(projectId, ProjectConfig(List(Rule.mergeAnything)))
      mr1 <- projectModifiers.open(projectId, "anyone@example.com", None)
      mr2 <- projectModifiers.open(projectId, "anyone@example.com", None)
      _   <- projectModifiers.finishPipeline(projectId, mr1)
      _   <- projectModifiers.finishPipeline(projectId, mr2)
      _   <- projectModifiers.setMergeability(projectId, mr2, Mergeability.NeedsRebase)

      result1 <- perform
      result2 <- perform
      result3 <- perform
    } yield {
      val (mergeRequestsAfterProcess1, logAfterProcess1) = result1
      val (mergeRequestsAfterProcess2, logAfterProcess2) = result2
      val (mergeRequestsAfterProcess3, logAfterProcess3) = result3

      val merge1 = ProjectAction.Merge(projectId, mr1)
      val rebase2 = ProjectAction.Rebase(projectId, mr2)
      val merge2 = ProjectAction.Merge(projectId, mr2)

      val firstMerged = expect(mergeRequestsAfterProcess1.map(_.mergeRequestIid) == List(mr2)) &&
        expect(logAfterProcess1 == List(merge1))

      val secondRebased = expect(mergeRequestsAfterProcess2.map(_.mergeRequestIid) == List(mr2)) &&
        expect(logAfterProcess2 == List(merge1, rebase2))

      val secondMerged = expect(mergeRequestsAfterProcess3.map(_.mergeRequestIid) == Nil) &&
        expect(logAfterProcess3 == List(merge1, rebase2, merge2))

      firstMerged &&
      secondRebased &&
      secondMerged
    }
  }

  testWithResources("known project with one mergeable MR - matching by author") { resources =>
    import resources._
    val projectId = 66L

    val project = Project(projectId)

    val correctDomainRegex = ".*@example.com".r

    val matchAuthorUsernameDomain =
      Rule("pipeline successful", Matcher.Author(TextMatcher.Matches(correctDomainRegex)), Action.Merge)

    for {
      _                         <- projectConfigModifiers.register(projectId, ProjectConfig(List(matchAuthorUsernameDomain)))
      mergeRequestId            <- projectModifiers.open(projectId, "anyone@example.com", None)
      _                         <- projectModifiers.finishPipeline(projectId, mergeRequestId)
      _                         <- process(WebhookEvent(project, "merge_request"))
      mergeRequestsAfterProcess <- resolver.resolve(project)
    } yield expect {
      mergeRequestsAfterProcess.map(_.mergeRequestIid) == Nil
    }
  }

}
