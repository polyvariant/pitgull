package io.pg.gitlab

import scala.util.chaining._

import caliban.client.CalibanClientError.DecodingError
import caliban.client.Operations.IsOperation
import caliban.client.SelectionBuilder
import cats.MonadError
import cats.kernel.Eq
import cats.syntax.all._
import cats.tagless.finalAlg
import ciris.Secret
import io.odin.Logger
import io.pg.gitlab.Gitlab.MergeRequestInfo
import io.pg.gitlab.GitlabEndpoints.transport.ApprovalRule
import io.pg.gitlab.graphql.MergeRequest
import io.pg.gitlab.graphql.MergeRequestConnection
import io.pg.gitlab.graphql.MergeRequestState
import io.pg.gitlab.graphql.Pipeline
import io.pg.gitlab.graphql.PipelineStatusEnum
import io.pg.gitlab.graphql.Project
import io.pg.gitlab.graphql.ProjectConnection
import io.pg.gitlab.graphql.Query
import io.pg.gitlab.graphql.User
import sttp.client3.Request
import sttp.client3.SttpBackend
import sttp.model.Uri
import sttp.tapir.Endpoint
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import fs2.Stream
import io.circe.{Codec => CirceCodec}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.Configuration
import io.pg.gitlab.GitlabEndpoints.transport.MergeRequestApprovals
import monocle.macros.Lenses
import cats.Show
import io.pg.TextUtils

@finalAlg
trait Gitlab[F[_]] {
  def mergeRequests(projectId: Long): F[List[MergeRequestInfo]]
  def acceptMergeRequest(projectId: Long, mergeRequestIid: Long): F[Unit]
  def rebaseMergeRequest(projectId: Long, mergeRequestIid: Long): F[Unit]
  def forceApprove(projectId: Long, mergeRequestIid: Long): F[Unit]
}

object Gitlab {

  // VCS-specific MR information
  // Not specific to the method of fetching (no graphql model references etc.)
  // Fields only required according to reason (e.g. must have a numeric ID - we might loosen this later)
  @Lenses
  final case class MergeRequestInfo(
    projectId: Long,
    mergeRequestIid: Long,
    status: Option[MergeRequestInfo.Status],
    authorUsername: String,
    description: Option[String],
    needsRebase: Boolean,
    hasConflicts: Boolean
  )

  object MergeRequestInfo {
    sealed trait Status extends Product with Serializable

    object Status {
      case object Success extends Status
      final case class Other(value: String) extends Status

      implicit val eq: Eq[Status] = Eq.fromUniversalEquals
    }

    implicit val showTrimmed: Show[MergeRequestInfo] =
      MergeRequestInfo.description.modify(_.map(TextUtils.trim(maxChars = 80))).apply(_).toString
  }

  def sttpInstance[F[_]: Logger: MonadError[*[_], Throwable]](
    baseUri: Uri,
    accessToken: Secret[String]
  )(
    implicit backend: SttpBackend[F, Any],
    backend2: sttp.client.SttpBackend[F, Nothing, sttp.client.NothingT],
    SC: fs2.Stream.Compiler[F, F]
  ): Gitlab[F] = {

    def runRequest[O](request: Request[O, Any]): F[O] =
      //todo multiple possible header names...
      request.header("Private-Token", accessToken.value).send(backend).map(_.body)

    //this is needed while caliban hasn't upgraded to sttp3
    def runRequestSttp2[O](request: sttp.client.Request[O, Nothing]): F[O] =
      //todo multiple possible header names...
      request.header("Private-Token", accessToken.value).send[F]().map(_.body)

    import sttp.tapir.client.sttp._

    def runEndpoint[I, E, O](
      endpoint: Endpoint[I, E, O, Any]
    ): I => F[Either[E, O]] =
      i => runRequest(SttpClientInterpreter.toRequestThrowDecodeFailures(endpoint, baseUri.some).apply(i))

    def runInfallibleEndpoint[I, O](
      endpoint: Endpoint[I, Nothing, O, Any]
    ): I => F[O] =
      runEndpoint[I, Nothing, O](endpoint).nested.map(_.merge).value

    def runGraphQLQuery[A: IsOperation, B](a: SelectionBuilder[A, B]): F[B] =
      runRequestSttp2(a.toRequest(baseUri.addPath("api", "graphql"))).rethrow

    new Gitlab[F] {
      def mergeRequests(projectId: Long): F[List[MergeRequestInfo]] =
        Logger[F].info(
          "Finding merge requests",
          Map(
            "projectId" -> projectId.show
          )
        ) *> Query
          .projects(ids = List(show"gid://gitlab/Project/$projectId").some)(
            ProjectConnection
              .nodes(
                Project
                  .mergeRequests(
                    state = MergeRequestState.opened.some
                  )(
                    MergeRequestConnection
                      .nodes(mergeRequestInfoSelection(projectId))
                  )
              )
              .map(flattenTheEarth)
          )
          .mapEither(_.toRight(DecodingError("Project not found")))
          .pipe(runGraphQLQuery(_))
          .flatTap { result =>
            Logger[F].info(
              "Found merge requests",
              Map("result" -> result.map(_.show).mkString)
            )
          }

      private def flattenTheEarth[A]: Option[List[Option[Option[Option[List[Option[A]]]]]]] => List[A] =
        _.toList.flatten.flatten.flatten.flatten.flatten.flatten

      private def mergeRequestInfoSelection(projectId: Long): SelectionBuilder[MergeRequest, MergeRequestInfo] = (
        MergeRequest.iid.mapEither(_.toLongOption.toRight(DecodingError("MR IID wasn't a Long"))) ~
          MergeRequest.headPipeline(Pipeline.status.map(convertPipelineStatus)) ~
          MergeRequest
            .author(User.username)
            .mapEither(_.toRight(DecodingError("MR has no author"))) ~
          MergeRequest.description ~
          MergeRequest.shouldBeRebased ~
          MergeRequest.conflicts
      ).mapN((buildMergeRequest(projectId) _))

      private def buildMergeRequest(
        projectId: Long
      )(
        mergeRequestIid: Long,
        status: Option[MergeRequestInfo.Status],
        authorUsername: String,
        description: Option[String],
        needsRebase: Boolean,
        hasConflicts: Boolean
      ): MergeRequestInfo = MergeRequestInfo(
        projectId = projectId,
        mergeRequestIid = mergeRequestIid,
        status = status,
        authorUsername = authorUsername,
        description = description,
        needsRebase = needsRebase,
        hasConflicts = hasConflicts
      )

      private val convertPipelineStatus: PipelineStatusEnum => MergeRequestInfo.Status = {
        case PipelineStatusEnum.SUCCESS => MergeRequestInfo.Status.Success
        case other                      => MergeRequestInfo.Status.Other(other.toString)
      }

      def acceptMergeRequest(projectId: Long, mergeRequestIid: Long): F[Unit] =
        runInfallibleEndpoint(GitlabEndpoints.acceptMergeRequest)
          .apply((projectId, mergeRequestIid))
          .void

      def rebaseMergeRequest(projectId: Long, mergeRequestIid: Long): F[Unit] =
        runInfallibleEndpoint(GitlabEndpoints.rebaseMergeRequest)
          .apply((projectId, mergeRequestIid))
          .void

      private def listMRApprovalRules(projectId: Long, mergeRequestIid: Long): F[List[ApprovalRule]] =
        runInfallibleEndpoint(GitlabEndpoints.listMRApprovaRules)
          .apply((projectId, mergeRequestIid))

      private def setMrRuleApprovals(projectId: Long, mergeRequestIid: Long, ruleId: Long, amount: Int): F[Unit] =
        runInfallibleEndpoint(GitlabEndpoints.setMRRuleApprovalRequirement)
          .apply((projectId, mergeRequestIid, ruleId, amount))

      private def getMrApprovals(projectId: Long, mergeRequestIid: Long): F[MergeRequestApprovals] =
        runInfallibleEndpoint(GitlabEndpoints.getMergeRequestApprovals)
          .apply((projectId, mergeRequestIid))

      private def setMrApprovals(projectId: Long, mergeRequestIid: Long, amount: Int): F[Unit] =
        runInfallibleEndpoint(GitlabEndpoints.setMergeRequestApprovals)
          .apply((projectId, mergeRequestIid, amount))

      def forceApprove(projectId: Long, mergeRequestIid: Long): F[Unit] = {
        val clearDirectApprovals = Stream
          .eval(getMrApprovals(projectId, mergeRequestIid))
          .filter(_.approvalsRequired > 0)
          .evalMap { _ =>
            setMrApprovals(projectId, mergeRequestIid, 0)
          }

        val removeMutableApprovalRules = Stream
          .evals(listMRApprovalRules(projectId, mergeRequestIid))
          .filter(_.isMutable)
          .evalMap { rule =>
            setMrRuleApprovals(projectId, mergeRequestIid, rule.id, 0)
          }

        clearDirectApprovals ++
          removeMutableApprovalRules
      }
        .compile
        .drain
    }
  }

  final case class GitlabError(msg: String) extends Throwable(s"Gitlab error: $msg")
}

object GitlabEndpoints {
  import sttp.tapir._

  private val baseEndpoint = infallibleEndpoint.in("api" / "v4")

  val acceptMergeRequest: Endpoint[(Long, Long), Nothing, Unit, Any] =
    baseEndpoint
      //hehe putin
      .put
      .in("projects" / path[Long]("projectId"))
      .in("merge_requests" / path[Long]("merge_request_iid"))
      .in("merge")

  val rebaseMergeRequest: Endpoint[(Long, Long), Nothing, Unit, Any] =
    baseEndpoint
      .put
      .in("projects" / path[Long]("projectId"))
      .in("merge_requests" / path[Long]("merge_request_iid"))
      .in("rebase")

  // Legacy methods, still in use though
  val getMergeRequestApprovals: Endpoint[(Long, Long), Nothing, MergeRequestApprovals, Any] =
    baseEndpoint
      .get
      .in("projects" / path[Long]("projectId"))
      .in("merge_requests" / path[Long]("merge_request_iid"))
      .in("approvals")
      .out(jsonBody[MergeRequestApprovals])

  val setMergeRequestApprovals: Endpoint[(Long, Long, Int), Nothing, Unit, Any] =
    baseEndpoint
      .post
      .in("projects" / path[Long]("projectId"))
      .in("merge_requests" / path[Long]("merge_request_iid"))
      .in("approvals")
      .in(query[Int]("approvals_required"))

  val listMRApprovaRules: Endpoint[(Long, Long), Nothing, List[ApprovalRule], Any] =
    baseEndpoint
      .get
      .in("projects" / path[Long]("projectId"))
      .in("merge_requests" / path[Long]("merge_request_iid"))
      .in("approval_rules")
      .out(
        jsonBody[List[ApprovalRule]]
      )

  val setMRRuleApprovalRequirement: Endpoint[(Long, Long, Long, Int), Nothing, Unit, Any] =
    baseEndpoint
      .put
      .in("projects" / path[Long]("projectId"))
      .in("merge_requests" / path[Long]("merge_request_iid"))
      .in("approval_rules" / path[Long]("approval_rule_id"))
      .in(query[Int]("approvals_required"))

  object transport {
    implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames

    final case class ApprovalRule(id: Long, name: String, ruleType: String) {
      val isMutable: Boolean = ruleType != "code_owner"
    }

    object ApprovalRule {
      implicit val codec: CirceCodec[ApprovalRule] = deriveConfiguredCodec
    }

    final case class MergeRequestApprovals(approvalsRequired: Int)

    object MergeRequestApprovals {
      implicit val codec: CirceCodec[MergeRequestApprovals] = deriveConfiguredCodec
    }

  }

}
