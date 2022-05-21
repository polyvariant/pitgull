package io.pg.transport

import io.circe.Codec

final case class MergeRequestState(
  projectId: Long,
  mergeRequestIid: Long,
  authorUsername: String,
  description: Option[String],
  status: MergeRequestState.Status,
  mergeability: MergeRequestState.Mergeability
) derives Codec.AsObject

object MergeRequestState {

  enum Status derives Codec.AsObject {
    case Success
    case Other(value: String)
  }

  enum Mergeability derives Codec.AsObject {
    case CanMerge, NeedsRebase, HasConflicts
  }

}
