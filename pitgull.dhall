let TextMatcher = < Equals : { value : Text } | Matches : { regex : Text } >

let List/map = https://prelude.dhall-lang.org/v16.0.0/List/map

let List/concat = https://prelude.dhall-lang.org/v16.0.0/List/concat

let MatcherFold =
      λ(M : Type) →
        { Author : { email : TextMatcher } → M
        , Description : { text : TextMatcher } → M
        , PipelineStatus : { status : Text } → M
        , Many : List M → M
        }

let Match
    : Type
    = ∀(M : Type) → MatcherFold M → M

let MatcherRaw =
      < Author : { email : TextMatcher }
      | Description : { text : TextMatcher }
      | PipelineStatus : { status : Text }
      >

let AllMatchers
    : MatcherFold (List MatcherRaw)
    = { Author =
          λ(args : { email : TextMatcher }) →
            [ MatcherRaw.Author { email = args.email } ]
      , Description =
          λ(args : { text : TextMatcher }) →
            [ MatcherRaw.Description { text = args.text } ]
      , PipelineStatus =
          λ(args : { status : Text }) →
            [ MatcherRaw.PipelineStatus { status = args.status } ]
      , Many =
          λ(matchers : List (List MatcherRaw)) → List/concat MatcherRaw matchers
      }

let Matcher
    : Match → List MatcherRaw
    = λ(m : Match) → m (List MatcherRaw) AllMatchers

let Author
    : { email : TextMatcher } → Match
    = λ(e : { email : TextMatcher }) →
      λ(M : Type) →
      λ(RC : MatcherFold M) →
        RC.Author { email = e.email }

let Description
    : { text : TextMatcher } → Match
    = λ(d : { text : TextMatcher }) →
      λ(M : Type) →
      λ(RC : MatcherFold M) →
        RC.Description { text = d.text }

let PipelineStatus
    : Text → Match
    = λ(status : Text) →
      λ(M : Type) →
      λ(RC : MatcherFold M) →
        RC.PipelineStatus { status }

let Many
    : List Match → Match
    = λ(elems : List Match) →
      λ(M : Type) →
      λ(RC : MatcherFold M) →
        RC.Many (List/map Match M (λ(y : Match) → y M RC) elems)

let Rule = { name : Text, matcher : List MatcherRaw }

let ProjectConfig = { rules : List Rule }

in  { TextMatcher
    , Author
    , Matcher
    , Description
    , PipelineStatus
    , Many
    , Rule
    , ProjectConfig
    }
