package org.polyvariant
import io.pg.gitlab.graphql.*

object Gitlab {
  def mergeRequestsQuery(projectFullPath: String) = Query.project("") {
    Project.mergeRequests()(
      MergeRequestConnection.edges{
        MergeRequestEdge.node {
          MergeRequest.title ~ MergeRequest.description ~ MergeRequest.author{
            UserCore.name
          }
        }
      }
    )
  }
}
