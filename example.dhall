let pg =
      https://raw.githubusercontent.com/pitgull/pitgull/v0.0.2/dhall/pitgull.dhall sha256:65a46e78c2d4aac7cd3afeb1fa209ed244dc60644634a9cfc61800ea3417ea9b

let wms =
      https://gitlab.com/kubukoz/demo/-/raw/db4686f29bab1bc056ec96307a39aa3dd6337173/wms.dhall sha256:4b9218b9a1a83262550b9bdfa7d7250f4aa365b8d8c2131f65517ef5f3eeb68c

in  pg.projectToJson { rules = [ wms.scalaSteward ] }
