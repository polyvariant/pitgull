let List/map =
      https://prelude.dhall-lang.org/v16.0.0/List/map sha256:dd845ffb4568d40327f2a817eb42d1c6138b929ca758d50bc33112ef3c885680

let List/concat =
      https://prelude.dhall-lang.org/v16.0.0/List/concat sha256:54e43278be13276e03bd1afa89e562e94a0a006377ebea7db14c7562b0de292b

let MergeRequestStatus
    : Type
    = < Success | Other : { s : Text } >

let MergeRequestInfo
    : Type
    = { status : MergeRequestStatus, authorUsername : Text, description : Text }

let Mismatch = < Message : Text >

let Matched = < Ok | NotOk : List Mismatch >

let Matched/combine
    : Matched -> Matched -> Matched
    = \(a : Matched) ->
      \(b : Matched) ->
        merge
          { Ok = b
          , NotOk =
              \(mismatchesLeft : List Mismatch) ->
                merge
                  { Ok = a
                  , NotOk =
                      \(mismatchesRight : List Mismatch) ->
                        Matched.NotOk
                          ( List/concat
                              Mismatch
                              [ mismatchesLeft, mismatchesRight ]
                          )
                  }
                  b
          }
          a

let MatcherFunction
    : Type
    = MergeRequestInfo -> Matched

let TextMatcher = Text -> Matched

let matchAny
    -- todo
    : List MatcherFunction -> MatcherFunction
    = \(matchers : List MatcherFunction) ->
      \(mr : MergeRequestInfo) ->
        Matched.Ok

let text/equalTo
    : Text -> TextMatcher
    = \(t : Text) -> \(t2 : Text) -> Matched.Ok

let text/matchesRegex
    : Text -> TextMatcher
    = \(t : Text) -> \(t2 : Text) -> Matched.Ok

let ops =
      let matchMany
          : List MatcherFunction -> MatcherFunction
          = \(functions : List MatcherFunction) ->
            \(mr : MergeRequestInfo) ->
              let results =
                    List/map
                      MatcherFunction
                      Matched
                      (\(F : MatcherFunction) -> F mr)
                      functions

              in  List/fold Matched results Matched Matched/combine Matched.Ok

      let statusSuccessful
          : MatcherFunction
          = \(MR : MergeRequestInfo) ->
              if    merge
                      { Success = True, Other = \(_ : { s : Text }) -> False }
                      MR.status
              then  Matched.Ok
              else  Matched.NotOk [ Mismatch.Message "Status mismatch" ]

      let descriptionMatches
          : TextMatcher -> MatcherFunction
          = \(descMatcher : TextMatcher) ->
            \(MR : MergeRequestInfo) ->
              descMatcher MR.description

      let authorMatches
          : TextMatcher -> MatcherFunction
          = \(authorMatcher : TextMatcher) ->
            \(MR : MergeRequestInfo) ->
              authorMatcher MR.authorUsername

      in  { matchMany
          , matchAny
          , statusSuccessful
          , descriptionMatches
          , authorMatches
          }

in  { MergeRequestStatus
    , MergeRequestInfo
    , Mismatch
    , MatcherFunction
    , ops
    , TextMatcher
    , text/equalTo
    , text/matchesRegex
    }
