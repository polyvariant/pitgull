package io.pg

import cats.tagless.finalAlg
import io.pg.gitlab.Gitlab
import io.pg.ProjectAction.Merge
import io.pg.config.ProjectConfig
import io.pg.config.Matcher
import cats.tagless.autoContravariant
import io.pg.config.Action

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

sealed trait ProjectAction extends Product with Serializable

object ProjectAction {
  final case class Merge(projectId: Long, mergeRequestIid: Long) extends ProjectAction
}
