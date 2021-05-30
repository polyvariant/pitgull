let pg = ./pitgull2.dhall

let ops = pg.ops

in  ops.matchMany
      [ ops.statusSuccessful
      , ops.matchAny
          [ ops.authorMatches (pg.text/equalTo "root@gmail.com")
          , ops.authorMatches (pg.text/equalTo "root-2@gmail.com")
          ]
      , ops.descriptionMatches
          (pg.text/matchesRegex ".*labels:.*semver-patch.*")
      ]
