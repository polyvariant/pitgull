let pg =
      https://raw.githubusercontent.com/pitgull/pitgull/v0.0.2/dhall/pitgull.dhall sha256:65a46e78c2d4aac7cd3afeb1fa209ed244dc60644634a9cfc61800ea3417ea9b

let wms =
      https://gitlab.com/kubukoz/demo/-/raw/294e0f709e270e26282dd6c7057a2592d1f85115/wms.dhall sha256:3f502b1907e329e6826068584fe3525303b472e3f249cff42388977835d20943

in  pg.projectToJson { rules = [ wms.scalaSteward ] }
