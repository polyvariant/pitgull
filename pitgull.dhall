let TextMatcher = < Equals : { value : Text } | Matches : { regex : Text } >

let Match =
      < Author : { email : TextMatcher }
      | Description : { text : TextMatcher }
      | PipelineStatus : { status : Text }
      >

let Rule = { name : Text, matches : List Match }

let ProjectConfig = { rules : List Rule }

let authorEmail =
      λ(email : Text) →
        Match.Author { email = TextMatcher.Equals { value = email } }

let descriptionRegex =
      λ(pattern : Text) →
        Match.Description { text = TextMatcher.Matches { regex = pattern } }

in  { TextMatcher, Match, Rule, ProjectConfig, authorEmail, descriptionRegex }
