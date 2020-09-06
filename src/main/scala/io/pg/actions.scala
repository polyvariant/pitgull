package io.pg

import cats.tagless.finalAlg
import io.pg.gitlab.Gitlab
import io.pg.ProjectAction.Merge
import io.pg.config.ProjectConfig
import io.pg.config.Matcher
import cats.tagless.autoContravariant
import io.pg.config.Action
import cats.data.EitherNel
import cats.implicits._
import cats.data.NonEmptyList
import io.pg.gitlab.webhook.WebhookEvent

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
    def matches(in: In): Matched[Unit]
  }

  object MatcherFunction {
    def fromPredicate[In](predicate: In => Boolean, orElse: In => Mismatch): MatcherFunction[In] =
      _.asRight[Mismatch].ensureOr(orElse)(predicate).toEitherNel.void
  }

  final case class Mismatch(reason: String)
  type Matched[A] = EitherNel[Mismatch, A]

  val isSuccessful: MatcherFunction[MergeRequestState] =
    MatcherFunction
      .fromPredicate[WebhookEvent.Pipeline.Status](
        _ === WebhookEvent.Pipeline.Status.Success,
        value => Mismatch(s"not successful, actual status: $value")
      )
      .contramap(_.status)

  //todo: matching logic :))
  //let the knife do the work
  val compileMatcher: Matcher => MatcherFunction[MergeRequestState] = _ => isSuccessful //todo

  def compile(state: MergeRequestState, project: ProjectConfig): List[Either[NonEmptyList[Mismatch], ProjectAction]] =
    project.rules.map { rule =>
      val ruleAction: ProjectAction = rule.action match {
        case Action.Merge => ProjectAction.Merge(state.projectId, state.mergeRequestIid)
      }

      compileMatcher(rule.matcher).matches(state).as(ruleAction)
    }

}

sealed trait ProjectAction extends Product with Serializable

object ProjectAction {
  final case class Merge(projectId: Long, mergeRequestIid: Long) extends ProjectAction
}
