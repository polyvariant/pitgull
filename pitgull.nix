let
  pkgs = import <nixpkgs> {};
  inherit (pkgs) lib;
  inherit (builtins) map;
in
{
  status = {
    success = "success";
    equals = expected: { status, ... }: status == expected;
  };
  author = {
    equals = expected: { author, ... }: author == expected;
  };
  description = {
    matches = regex: { description, ... }: builtins.match regex description != null;
  };
  allOf = matchers: input: !builtins.elem false (map (a: a input) matchers);
  anyOf = matchers: input: builtins.elem true (map (a: a input) matchers);
}
