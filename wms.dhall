let pg = ./pitgull.dhall

let scalaSteward
    : pg.Rule
    = { name = "Scala Steward"
      , matcher =
          pg.Matcher
            ( pg.Many
                [ pg.Author
                    { email =
                        pg.TextMatcher.Equals
                          { value = "scala.steward@ocado.com" }
                    }
                , pg.Description
                    { text =
                        pg.TextMatcher.Matches
                          { regex = "*labels:.*semver-patch.*" }
                    }
                , pg.PipelineStatus "success"
                ]
            )
      }

in  { scalaSteward }
