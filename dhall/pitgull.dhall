let pg = ./core.dhall

let projectToJson = ./json.dhall

in  { text = pg.text, match = pg.match, Rule = pg.Rule, projectToJson }
