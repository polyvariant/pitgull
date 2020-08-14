let pg =
      ./pitgull.dhall sha256:0bb532fcb24bfdd8577cd96a908acbd5a9413b2c9685c7d510cee34ceb376c9a

let scalaSteward
    : pg.Rule
    = { name = "Scala Steward"
      , matches =
        [ pg.authorEmail "scala.steward@ocado.com"
        , pg.descriptionRegex "*labels:.*semver-patch.*"
        , pg.Match.PipelineStatus { status = "success" }
        ]
      }

in  { rules = [ scalaSteward ] } : pg.ProjectConfig
