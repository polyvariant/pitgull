let pg = ./pitgull.dhall

let wms = ./wms.dhall

in  { rules = [ wms.scalaSteward ] } : pg.ProjectConfig
