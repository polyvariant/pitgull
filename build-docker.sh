#!/bin/bash

docker run \
  --mount="type=bind,source=$(pwd)/docker.nix,target=/opt/docker.nix" \
  --mount="type=bind,source=$(pwd)/target/universal/stage/,target=/opt/stage/" \
  --mount="type=bind,source=$(pwd)/out/,target=/opt/out/" \
  --mount="type=bind,source=/var/run/docker.sock,target=/var/run/docker.sock" \
  --rm \
  lnl7/nix:2.3.7 \
  nix-build /opt/docker.nix
cat out/out.tar.gz | docker import - demo:0.0.0
