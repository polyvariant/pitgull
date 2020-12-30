package io.pg

import cats.data.EitherNel
import cats.implicits._
import cats.tagless.autoContravariant
import cats.tagless.finalAlg
import io.pg.ProjectAction.Merge
import io.pg.config.Action
import io.pg.config.Matcher
import io.pg.config.ProjectConfig
import io.pg.gitlab.Gitlab
import io.odin.Logger
import io.pg.Prelude.MonadThrow
import io.pg.gitlab.Gitlab.MergeRequestInfo
import io.pg.config.TextMatcher
import cats.MonoidK
import cats.Show
import io.pg.ProjectAction.Rebase

@finalAlg
trait ProjectActions[F[_]] {
  def execute(action: ProjectAction): F[Unit]
}

object ProjectActions {

  def instance[F[_]: Gitlab: Logger: MonadThrow]: ProjectActions[F] = action => {
    val logBefore = Logger[F].info("About to execute action", Map("action" -> action.toString))

    val perform = action match {
      //todo: perform check is the MR still open?
      //or fall back in case it's not
      //https://www.youtube.com/watch?v=vxKBHX9Datw
      case Merge(projectId, mergeRequestIid) =>
        Gitlab[F].acceptMergeRequest(projectId, mergeRequestIid)

      case Rebase(projectId, mergeRequestIid) =>
        Gitlab[F].rebaseMergeRequest(projectId, mergeRequestIid)
    }

    logBefore *> perform.handleErrorWith { error =>
      Logger[F]
        .error(
          "Couldn't perform action",
          Map(
            //todo: consier granular fields
            "action" -> action.toString
          ),
          error
        )
    }
  }

  @autoContravariant
  trait MatcherFunction[-In] {
    def matches(in: In): Matched[Unit]
  }

  object MatcherFunction {

    implicit val monoidK: MonoidK[MatcherFunction] = new MonoidK[MatcherFunction] {
      override def combineK[A](x: MatcherFunction[A], y: MatcherFunction[A]): MatcherFunction[A] =
        in => (x.matches(in).toValidated |+| y.matches(in).toValidated).toEither
      override def empty[A]: MatcherFunction[A] = success
    }

    def fromPredicate[In](
      predicate: In => Boolean,
      orElse: In => Mismatch
    ): MatcherFunction[In] =
      _.asRight[Mismatch].ensureOr(orElse)(predicate).toEitherNel.void

    val success: MatcherFunction[Any] =
      _.pure[Matched].void
  }

  final case class Mismatch(reason: String)

  object Mismatch {
    implicit val show: Show[Mismatch] = Show.fromToString
  }

  type Matched[A] = EitherNel[Mismatch, A]

  def statusMatches(expectedStatus: String): MatcherFunction[MergeRequestState] =
    MatcherFunction
      .fromPredicate[MergeRequestInfo.Status](
        {
          case MergeRequestInfo.Status.Success      => expectedStatus.toLowerCase === "success"
          case MergeRequestInfo.Status.Other(value) => expectedStatus === value
        },
        value => Mismatch(s"""Status is not "$expectedStatus", actual status: "$value"""")
      )
      .contramap(_.status)

  val matchTextMatcher: TextMatcher => MatcherFunction[String] = {
    case TextMatcher.Equals(expected) =>
      MatcherFunction.fromPredicate[String](
        _ === expected,
        value => Mismatch(show"invalid value, expected $expected, got $value")
      )
    case TextMatcher.Matches(regex)   =>
      MatcherFunction.fromPredicate[String](
        regex.matches,
        value => Mismatch(show"invalid value, expected to match ${regex.toString}, got $value")
      )
  }

  def exists[A](base: MatcherFunction[A]): MatcherFunction[Option[A]] =
    _.fold[Matched[Unit]](Mismatch("Option was empty").leftNel)(base.matches)

  def autorMatches(matcher: TextMatcher): MatcherFunction[MergeRequestState] =
    exists(matchTextMatcher(matcher))
      .contramap(_.authorEmail)

  def descriptionMatches(matcher: TextMatcher): MatcherFunction[MergeRequestState] =
    exists(matchTextMatcher(matcher))
      .contramap(_.description)

  val compileMatcher: Matcher => MatcherFunction[MergeRequestState] = {
    case Matcher.Author(email)          => autorMatches(email)
    case Matcher.Description(text)      => descriptionMatches(text)
    case Matcher.PipelineStatus(status) => statusMatches(status)
    case Matcher.Many(values)           => values.foldMapK(compileMatcher)
  }

  // def compile(
  //   state: MergeRequestState,
  //   project: ProjectConfig
  // ): List[EitherNel[Mismatch, ProjectAction]] =
  //   project.rules.map { rule =>
  //     val ruleAction: ProjectAction = rule.action match {
  //       case Action.Merge =>
  //         ProjectAction.Merge(state.projectId, state.mergeRequestIid)
  //     }

  //     compileMatcher(rule.matcher).matches(state).as(ruleAction)
  //   }
  def compile(
    state: MergeRequestState,
    project: ProjectConfig
  ): List[EitherNel[Mismatch, MergeRequestState]] =
    project.rules.map { rule =>
      compileMatcher(rule.matcher).matches(state).as(state)
    }

}

sealed trait ProjectAction extends Product with Serializable

object ProjectAction {
  final case class Merge(projectId: Long, mergeRequestIid: Long) extends ProjectAction
  final case class Rebase(projectId: Long, mergeRequestIid: Long) extends ProjectAction
}
