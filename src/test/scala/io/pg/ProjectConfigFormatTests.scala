package io.pg

import weaver._
import io.circe.literal._
import io.pg.config.ProjectConfig
import io.pg.config.ProjectConfigReader
import io.pg.gitlab.webhook.Project
import io.pg.config.Rule
import io.pg.config.Action
import io.pg.config.Matcher
import io.pg.config.TextMatcher
import io.circe.syntax._

object ProjectConfigFormatTest extends FunSuite {

  val asJSON = json"""{
"rules": [
  {
    "action": "Merge",
    "matcher": {
      "kind": "Many",
      "values": [
        {
          "email": {
            "kind": "Equals",
            "value": "scala.steward@ocado.com"
          },
          "kind": "Author"
        },
        {
          "kind": "Description",
          "text": {
            "kind": "Matches",
            "regex": ".*labels:.*semver-patch.*"
          }
        },
        {
          "kind": "PipelineStatus",
          "status": "success"
        }
      ]
    },
    "name": "Scala Steward"
  }
]
}
"""

  val decoded = ProjectConfig(
    rules = List(
      Rule(
        name = "Scala Steward",
        action = Action.Merge,
        matcher = Matcher.Many(
          List(
            Matcher.Author(TextMatcher.Equals("scala.steward@ocado.com")),
            Matcher.Description(TextMatcher.Matches(".*labels:.*semver-patch.*".r)),
            Matcher.PipelineStatus("success")
          )
        )
      )
    )
  )

  test("Example config can be decoded") {
    val actual = asJSON.as[ProjectConfig]
    assert(actual == Right(decoded))
  }
  test("Example config can be encoded") {
    val actual = decoded.asJson
    assert.eql(actual, asJSON)
  }
}
