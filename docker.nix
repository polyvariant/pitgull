let
  pkgs = import <nixpkgs> { };
  binary = /opt/stage/bin/pitgull;

  image = pkgs.dockerTools.buildImage {
    name = "test";
    fromImageName = "adoptopenjdk/openjdk11";
    fromImageTag = "jre-11.0.8_10-alpine";
    contents = pkgs.dhall-json;
    config = { Cmd = [ "${binary}" ]; };
  };
in pkgs.stdenv.mkDerivation {
  name = "demo";
  # buildInputs = [ pkgs.docker ];
  src = image;
  installPhase = ''
    cp $src /opt/out/out.tar.gz
  '';
}

