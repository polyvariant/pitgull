let
  pkgs = import <nixpkgs> {};
  inherit (pkgs) lib;
  inherit (builtins) map;

  mkMismatch = kind: expected: { inherit kind expected; };

  mismatches = {
    status = mkMismatch "status";
    author = mkMismatch "author";
    description = mkMismatch "description";
    regex = mkMismatch "regex";
    text = {
      equal = mkMismatch "equal";
      matches = pattern: { kind = "matches";inherit pattern; };
    };
    noneOf = mkMismatch "noneOf";
  };
  results = rec {
    ok = { kind = "ok"; };
    notOk = mismatch: notOkMany [ mismatch ];
    notOkMany = mismatches: {
      kind = "notOk";
      inherit mismatches;
    };
  };
  ensureOr = bool: msg: if bool then results.ok else results.notOk msg;
  allResults = matchers: input:
    let
      results = (map (a: a input) matchers);
      failed = builtins.filter ({ kind, ... }: kind == "notOk") results;
      passed = builtins.filter ({ kind, ... }: kind == "ok") results;
    in
      {
        failed = builtins.concatMap ({ mismatches, ... }: mismatches) failed;
        passedCount = builtins.length passed;
      };
in
{
  status = {
    success = "success";
    equals = expected: { status, ... }: ensureOr (status == expected) (mismatches.status expected);
  };
  author = {
    equals = expected: { author, ... }: ensureOr (author == expected) (mismatches.author (mismatches.text.equal expected));
  };
  description = {
    matches = regex: { description, ... }: ensureOr (builtins.match regex description != null) (mismatches.description (mismatches.text.matches regex));
  };
  allOf = matchers: input:
    let
      out = allResults matchers input;
    in
      if builtins.length out.failed == 0 then results.ok else results.notOkMany out.failed;

  anyOf = matchers: input:
    let
      out = allResults matchers input;
    in
      ensureOr (out.passedCount > 0) (mismatches.noneOf out.failed);
}
