let pg =
      ./pitgull.dhall sha256:0bb532fcb24bfdd8577cd96a908acbd5a9413b2c9685c7d510cee34ceb376c9a

let wms =
      http://localhost:8080/wms.dhall using [ { mapKey = "TOKEN"
                                              , mapValue =
                                                  env:TOKEN as Text ? ""
                                              }
                                            ] sha256:383d083633c36aad1ede59725eed7565100db721173ee82c0d395bab5bef4fe7

in  { rules = [ wms.scalaSteward ] } : pg.ProjectConfig
