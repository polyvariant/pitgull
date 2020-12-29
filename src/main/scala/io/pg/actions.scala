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

@finalAlg
trait ProjectActions[F[_]] {
  def execute(action: ProjectAction): F[Unit]
}

object ProjectActions {

  def instance[F[_]: Gitlab: Logger: MonadThrow]: ProjectActions[F] = {
    //todo: perform check is the MR still open?
    //or fall back in case it's not
    //https://www.youtube.com/watch?v=vxKBHX9Datw
    case Merge(projectId, mergeRequestIid) =>
      Gitlab[F].acceptMergeRequest(projectId, mergeRequestIid).handleErrorWith { error =>
        Logger[F]
          .error(
            "Couldn't accept merge request",
            Map(
              "projectId" -> projectId.toString(),
              "mergeRequestIid" -> mergeRequestIid.toString()
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
      override def empty[A]: MatcherFunction[A] = _ => Right(())
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
  type Matched[A] = EitherNel[Mismatch, A]

  val isSuccessful: MatcherFunction[MergeRequestState] =
    MatcherFunction
      .fromPredicate[MergeRequestInfo.Status](
        _ === MergeRequestInfo.Status.Success,
        value => Mismatch(s"not successful, actual status: $value")
      )
      .contramap(_.status)

  def matchTextMatcher: TextMatcher => MatcherFunction[String] = {
    case TextMatcher.Equals(expected) => 
      MatcherFunction.fromPredicate[String](
        _ === expected,
        value => Mismatch(show"invalid value, expected $expected, got $value")
      )
    case TextMatcher.Matches(regex) => 
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
    case Matcher.Author(email) => autorMatches(email)
    case Matcher.Description(text) => descriptionMatches(text)
    case Matcher.PipelineStatus(status) => isSuccessful 
    case Matcher.Many(Nil) => MatcherFunction.success
    case Matcher.Many(values) => values.foldMapK(compileMatcher)
  }

  def compile(
    state: MergeRequestState,
    project: ProjectConfig
  ): List[EitherNel[Mismatch, ProjectAction]] =
    project.rules.map { rule =>
      val ruleAction: ProjectAction = rule.action match {
        case Action.Merge =>
          ProjectAction.Merge(state.projectId, state.mergeRequestIid)
      }

      compileMatcher(rule.matcher).matches(state).as(ruleAction)
    }

}

sealed trait ProjectAction extends Product with Serializable

object ProjectAction {
  final case class Merge(projectId: Long, mergeRequestIid: Long) extends ProjectAction
}
