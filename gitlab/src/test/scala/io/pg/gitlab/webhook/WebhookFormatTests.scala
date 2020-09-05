package io.pg.gitlab.webhook

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import cats.syntax.all._

class WebhookFormatTests extends AnyWordSpec with Matchers {
  import io.circe.literal._

  "Webhook events" when {
    "push event" in {
      json"""
      {
  "object_kind": "push",
  "before": "95790bf891e76fee5e1747ab589903a6a1f80f22",
  "after": "da1560886d4f094c3e6c9ef40349f7d38b5d27d7",
  "ref": "refs/heads/master",
  "checkout_sha": "da1560886d4f094c3e6c9ef40349f7d38b5d27d7",
  "user_id": 4,
  "user_name": "John Smith",
  "user_username": "jsmith",
  "user_email": "john@example.com",
  "user_avatar": "https://s.gravatar.com/avatar/d4c74594d841139328695756648b6bd6?s=8://s.gravatar.com/avatar/d4c74594d841139328695756648b6bd6?s=80",
  "project_id": 15,
  "project":{
    "id": 15,
    "name":"Diaspora",
    "description":"",
    "web_url":"http://example.com/mike/diaspora",
    "avatar_url":null,
    "git_ssh_url":"git@example.com:mike/diaspora.git",
    "git_http_url":"http://example.com/mike/diaspora.git",
    "namespace":"Mike",
    "visibility_level":0,
    "path_with_namespace":"mike/diaspora",
    "default_branch":"master",
    "homepage":"http://example.com/mike/diaspora",
    "ssh_url":"git@example.com:mike/diaspora.git",
    "http_url":"http://example.com/mike/diaspora.git"
  },
  "repository":{
    "name": "Diaspora",
    "url": "git@example.com:mike/diaspora.git",
    "description": "",
    "homepage": "http://example.com/mike/diaspora",
    "git_http_url":"http://example.com/mike/diaspora.git",
    "git_ssh_url":"git@example.com:mike/diaspora.git",
    "visibility_level":0
  },
  "commits": [
    {
      "id": "b6568db1bc1dcd7f8b4d5a946b0b91f9dacd7327",
      "message": "Update Catalan translation to e38cb41.\n\nSee https://gitlab.com/gitlab-org/gitlab for more information",
      "title": "Update Catalan translation to e38cb41.",
      "timestamp": "2011-12-12T14:27:31+02:00",
      "url": "http://example.com/mike/diaspora/commit/b6568db1bc1dcd7f8b4d5a946b0b91f9dacd7327",
      "author": {
        "name": "Jordi Mallach",
        "email": "jordi@softcatala.org"
      },
      "added": ["CHANGELOG"],
      "modified": ["app/controller/application.rb"],
      "removed": []
    },
    {
      "id": "da1560886d4f094c3e6c9ef40349f7d38b5d27d7",
      "message": "fixed readme",
      "title": "fixed readme",
      "timestamp": "2012-01-03T23:36:29+02:00",
      "url": "http://example.com/mike/diaspora/commit/da1560886d4f094c3e6c9ef40349f7d38b5d27d7",
      "author": {
        "name": "GitLab dev user",
        "email": "gitlabdev@dv6700.(none)"
      },
      "added": ["CHANGELOG"],
      "modified": ["app/controller/application.rb"],
      "removed": []
    }
  ],
  "total_commits_count": 4
}""".as[WebhookEvent] shouldBe WebhookEvent
        .Push(
          Project(
            id = 15,
            name = "Diaspora",
            pathWithNamespace = "mike/diaspora",
            defaultBranch = "master"
          )
        )
        .asRight
    }

    "pipeline event" in {
      json"""
      {
   "object_kind": "pipeline",
   "object_attributes":{
      "id": 31,
      "ref": "master",
      "tag": false,
      "sha": "bcbb5ec396a2c0f828686f14fac9b80b780504f2",
      "before_sha": "bcbb5ec396a2c0f828686f14fac9b80b780504f2",
      "source": "merge_request_event",
      "status": "success",
      "stages":[
         "build",
         "test",
         "deploy"
      ],
      "created_at": "2016-08-12 15:23:28 UTC",
      "finished_at": "2016-08-12 15:26:29 UTC",
      "duration": 63,
      "variables": [
        {
          "key": "NESTOR_PROD_ENVIRONMENT",
          "value": "us-west-1"
        }
      ]
   },
    "merge_request": {
      "id": 1,
      "iid": 1,
      "title": "Test",
      "source_branch": "test",
      "source_project_id": 1,
      "target_branch": "master",
      "target_project_id": 1,
      "state": "opened",
      "merge_status": "can_be_merged",
      "url": "http://192.168.64.1:3005/gitlab-org/gitlab-test/merge_requests/1"
   },
   "user":{
      "name": "Administrator",
      "username": "root",
      "avatar_url": "http://www.gravatar.com/avatar/e32bd13e2add097461cb96824b7a829c?s=80\u0026d=identicon",
      "email": "user_email@gitlab.com"
   },
   "project":{
      "id": 1,
      "name": "Gitlab Test",
      "description": "Atque in sunt eos similique dolores voluptatem.",
      "web_url": "http://192.168.64.1:3005/gitlab-org/gitlab-test",
      "avatar_url": null,
      "git_ssh_url": "git@192.168.64.1:gitlab-org/gitlab-test.git",
      "git_http_url": "http://192.168.64.1:3005/gitlab-org/gitlab-test.git",
      "namespace": "Gitlab Org",
      "visibility_level": 20,
      "path_with_namespace": "gitlab-org/gitlab-test",
      "default_branch": "master"
   },
   "commit":{
      "id": "bcbb5ec396a2c0f828686f14fac9b80b780504f2",
      "message": "test\n",
      "timestamp": "2016-08-12T17:23:21+02:00",
      "url": "http://example.com/gitlab-org/gitlab-test/commit/bcbb5ec396a2c0f828686f14fac9b80b780504f2",
      "author":{
         "name": "User",
         "email": "user@gitlab.com"
      }
   },
   "builds":[
      {
         "id": 380,
         "stage": "deploy",
         "name": "production",
         "status": "skipped",
         "created_at": "2016-08-12 15:23:28 UTC",
         "started_at": null,
         "finished_at": null,
         "when": "manual",
         "manual": true,
         "allow_failure": false,
         "user":{
            "name": "Administrator",
            "username": "root",
            "avatar_url": "http://www.gravatar.com/avatar/e32bd13e2add097461cb96824b7a829c?s=80\u0026d=identicon"
         },
         "runner": null,
         "artifacts_file":{
            "filename": null,
            "size": null
         }
      },
      {
         "id": 377,
         "stage": "test",
         "name": "test-image",
         "status": "success",
         "created_at": "2016-08-12 15:23:28 UTC",
         "started_at": "2016-08-12 15:26:12 UTC",
         "finished_at": null,
         "when": "on_success",
         "manual": false,
         "allow_failure": false,
         "user":{
            "name": "Administrator",
            "username": "root",
            "avatar_url": "http://www.gravatar.com/avatar/e32bd13e2add097461cb96824b7a829c?s=80\u0026d=identicon"
         },
         "runner": {
            "id":380987,
            "description":"shared-runners-manager-6.gitlab.com",
            "active":true,
            "is_shared":true
         },
         "artifacts_file":{
            "filename": null,
            "size": null
         }
      },
      {
         "id": 378,
         "stage": "test",
         "name": "test-build",
         "status": "success",
         "created_at": "2016-08-12 15:23:28 UTC",
         "started_at": "2016-08-12 15:26:12 UTC",
         "finished_at": "2016-08-12 15:26:29 UTC",
         "when": "on_success",
         "manual": false,
         "allow_failure": false,
         "user":{
            "name": "Administrator",
            "username": "root",
            "avatar_url": "http://www.gravatar.com/avatar/e32bd13e2add097461cb96824b7a829c?s=80\u0026d=identicon"
         },
         "runner": {
            "id":380987,
            "description":"shared-runners-manager-6.gitlab.com",
            "active":true,
            "is_shared":true
         },
         "artifacts_file":{
            "filename": null,
            "size": null
         }
      },
      {
         "id": 376,
         "stage": "build",
         "name": "build-image",
         "status": "success",
         "created_at": "2016-08-12 15:23:28 UTC",
         "started_at": "2016-08-12 15:24:56 UTC",
         "finished_at": "2016-08-12 15:25:26 UTC",
         "when": "on_success",
         "manual": false,
         "allow_failure": false,
         "user":{
            "name": "Administrator",
            "username": "root",
            "avatar_url": "http://www.gravatar.com/avatar/e32bd13e2add097461cb96824b7a829c?s=80\u0026d=identicon"
         },
         "runner": {
            "id":380987,
            "description":"shared-runners-manager-6.gitlab.com",
            "active":true,
            "is_shared":true
         },
         "artifacts_file":{
            "filename": null,
            "size": null
         }
      },
      {
         "id": 379,
         "stage": "deploy",
         "name": "staging",
         "status": "created",
         "created_at": "2016-08-12 15:23:28 UTC",
         "started_at": null,
         "finished_at": null,
         "when": "on_success",
         "manual": false,
         "allow_failure": false,
         "user":{
            "name": "Administrator",
            "username": "root",
            "avatar_url": "http://www.gravatar.com/avatar/e32bd13e2add097461cb96824b7a829c?s=80\u0026d=identicon"
         },
         "runner": null,
         "artifacts_file":{
            "filename": null,
            "size": null
         }
      }
   ]
}""".as[WebhookEvent] shouldBe WebhookEvent
        .Pipeline(
          MergeRequest(1L /* , MergeRequest.State.Opened */ ).some,
          Project(id = 1L, name = "Gitlab Test", pathWithNamespace = "gitlab-org/gitlab-test", defaultBranch = "master"),
          WebhookEvent
            .Pipeline
            .Attributes(id = 31L, ref = "master", status = WebhookEvent.Pipeline.Status.Success)
        )
        .asRight
    }
  }
}
