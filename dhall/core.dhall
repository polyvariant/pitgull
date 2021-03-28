let List/map =
      https://prelude.dhall-lang.org/v16.0.0/List/map sha256:dd845ffb4568d40327f2a817eb42d1c6138b929ca758d50bc33112ef3c885680

let TextMatcherFold =
      λ(Result : Type) →
        { Equals : { value : Text } → Result
        , Matches : { regex : Text } → Result
        }

let TextMatcher
    : Type
    = ∀(Result : Type) → TextMatcherFold Result → Result

let text =
        { Equals =
            λ(args : { value : Text }) →
            λ(Result : Type) →
            λ(Fold : TextMatcherFold Result) →
              Fold.Equals { value = args.value }
        , Matches =
            λ(args : { regex : Text }) →
            λ(Result : Type) →
            λ(Fold : TextMatcherFold Result) →
              Fold.Matches { regex = args.regex }
        }
      : { Equals : { value : Text } → TextMatcher
        , Matches : { regex : Text } → TextMatcher
        }

let MatcherFold =
      λ(M : Type) →
        { Author : { email : TextMatcher } → M
        , Description : { text : TextMatcher } → M
        , PipelineStatus : { status : Text } → M
        , Many : List M → M
        , OneOf : List M → M
        , Not : M -> M
        }

let Matcher
    : Type
    = ∀(M : Type) → MatcherFold M → M

let listOf =
      λ(elems : List Matcher) →
      λ(M : Type) →
      λ(path : MatcherFold M → List M → M) →
      λ(RC : MatcherFold M) →
        let foldChild
            : Matcher → M
            = λ(y : Matcher) → y M RC

        let folded = List/map Matcher M foldChild elems

        in  path RC folded

let match =
        { Author =
            λ(args : { email : TextMatcher }) →
            λ(M : Type) →
            λ(RC : MatcherFold M) →
              RC.Author { email = args.email }
        , Description =
            λ(args : { text : TextMatcher }) →
            λ(M : Type) →
            λ(RC : MatcherFold M) →
              RC.Description { text = args.text }
        , PipelineStatus =
            λ(status : Text) →
            λ(M : Type) →
            λ(RC : MatcherFold M) →
              RC.PipelineStatus { status }
        , Many =
            λ(elems : List Matcher) →
            λ(M : Type) →
              listOf elems M (λ(RC : MatcherFold M) → RC.Many)
        , OneOf =
            λ(elems : List Matcher) →
            λ(M : Type) →
              listOf elems M (λ(RC : MatcherFold M) → RC.OneOf)
        }
      : { Author : { email : TextMatcher } → Matcher
        , Description : { text : TextMatcher } → Matcher
        , PipelineStatus : Text → Matcher
        , Many : List Matcher → Matcher
        , OneOf : List Matcher → Matcher
        }

let ActionFold = λ(M : Type) → { Merge : M }

let Action = ∀(M : Type) → ActionFold M → M

let action =
        { Merge = λ(M : Type) → λ(AF : ActionFold M) → AF.Merge }
      : { Merge : Action }

let Rule = { name : Text, matcher : Matcher, action : Action }

let ProjectConfig = { rules : List Rule }

in  { TextMatcherFold
    , TextMatcher
    , text
    , MatcherFold
    , Matcher
    , match
    , Action
    , action
    , Rule
    , ProjectConfig
    }
