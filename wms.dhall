let pg = ./dhall/pitgull.dhall

let scalaSteward
    : pg.Rule
    = { name = "Scala Steward"
      , matcher =
          pg.match.Many
            [ pg.match.Author
                { email = pg.text.Equals { value = "scala.steward@ocado.com" } }
            , pg.match.Description
                { text = pg.text.Matches { regex = "*labels:.*semver-patch.*" }
                }
            , pg.match.PipelineStatus "success"
            ]
      }

in  { scalaSteward }
