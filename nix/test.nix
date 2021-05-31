let
  pkgs = import <nixpkgs> {};
  run = import ../wms.nix;
in

run { status = "success"; author = "user1@gmail.com"; description = "hello werld"; }
