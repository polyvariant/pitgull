package io.pg.webhook

import cats.data.NonEmptyList
import sttp.tapir.server.ServerEndpoint
import io.pg.gitlab.webhook.WebhookEvent
import io.pg.messaging.Publisher
import io.pg.messaging.Processor
import io.odin.Logger
import cats.Applicative
import io.pg.config.ProjectConfigReader
import io.pg.config.ProjectConfig
import cats.tagless.finalAlg
import io.pg.gitlab.Gitlab
import io.pg.webhook.ProjectAction.Merge
import cats.implicits._
import cats.MonadError
import cats.tagless.autoContravariant
import io.pg.config.Action
import io.pg.config.Matcher
import cats.data.OptionT
import io.pg.gitlab.graphql.MergeRequest
import io.pg.gitlab.graphql.User
import io.pg.gitlab.Gitlab.GitlabError

object WebhookRouter {

  object endpoints {
    import sttp.tapir._
    import sttp.tapir.json.circe._

    val webhook = infallibleEndpoint.post.in("webhook").in(jsonBody[WebhookEvent])
  }

  def routes[F[_]: Applicative](eventPublisher: Publisher[F, WebhookEvent]): NonEmptyList[ServerEndpoint[_, _, _, Nothing, F]] =
    NonEmptyList.of(
      endpoints.webhook.serverLogicRecoverErrors(eventPublisher.publish)
    )

}

@finalAlg
trait ProjectActions[F[_]] {
  def execute(action: ProjectAction): F[Unit]
}

object ProjectActions {

  def instance[F[_]: Gitlab]: ProjectActions[F] = {
    //todo: perform check is the MR still open?
    //or fall back in case it's not
    //https://www.youtube.com/watch?v=vxKBHX9Datw
    case Merge(projectId, mergeRequestIid) => Gitlab[F].acceptMergeRequest(projectId, mergeRequestIid)
  }

  @autoContravariant
  trait MatcherFunction[-In] {
    def matches(in: In): Boolean //todo eithernel with reason for not matching
  }

  //todo: matching logic :))
  //let the knife do the work
  val compileMatcher: Matcher => MatcherFunction[MergeRequestState] = _ => _ => true //todo

  def compile(state: MergeRequestState, project: ProjectConfig): List[ProjectAction] =
    project.rules.flatMap { rule =>
      val ruleActions = rule.action match {
        case Action.Merge => List(ProjectAction.Merge(state.projectId, state.mergeRequestIid))
      }

      if (compileMatcher(rule.matcher).matches(state)) ruleActions
      else Nil
    }

}

//current MR state - rebuilt on every event.
//Checked against rules to come up with a decision.
final case class MergeRequestState(projectId: Long, mergeRequestIid: Long, authorEmail: String, description: Option[String])

sealed trait ProjectAction extends Product with Serializable

object ProjectAction {
  final case class Merge(projectId: Long, mergeRequestIid: Long) extends ProjectAction
}

object WebhookProcessor {

  //Option - some events don't yield a state to work with and should be ignored.
  //We should get an ADT for this.
  def buildStateFromEvent[F[_]: Gitlab: Logger: MonadError[*[_], Throwable]]: WebhookEvent => F[Option[MergeRequestState]] = {
    case WebhookEvent.Pipeline(Some(mr), project)           =>
      //todo extract somewhere
      Gitlab[F]
        .mergeRequestInfo(projectPath = project.pathWithNamespace, mergeRequestIId = mr.iid.toString)(
          MergeRequest.author(User.email) ~ MergeRequest.description
        )
        .flatMap(_.leftTraverse(_.liftTo[F](GitlabError("MR author missing"))))
        .map {
          case (Some(email), description) => MergeRequestState(project.id, mr.iid, email, description).some
          case _                          => none
        }

    case p: WebhookEvent.Pipeline if p.mergeRequest.isEmpty => Logger[F].info("Ignoring pipeline event detached from MR").as(none)
    case e                                                  => Logger[F].info("Ignoring event", Map("event" -> e.toString())).as(none)
  }

  //todo: gitlab shouldn't be used here directly
  def instance[F[_]: ProjectConfigReader: ProjectActions: Gitlab: Logger: MonadError[*[_], Throwable]]: Processor[F, WebhookEvent] =
    Processor.simple { ev =>
      val logReceived = Logger[F].info("Received event", Map("event" -> ev.toString()))

      for {
        _      <- logReceived
        config <- ProjectConfigReader[F].readConfig
        state  <- buildStateFromEvent[F].apply(ev)
        actions = state.traverse(ProjectActions.compile(_, config)).flattenOption
        _      <- Logger[F].debug("All actions to execute", Map("actions" -> actions.toString))
        _      <- actions.traverse_ { action =>
                    Logger[F].info("About to execute action", Map("action" -> action.toString))
                  }
      } yield ()
    }

}
