let pg = ./dhall/pitgull.dhall

let wms = ./wms.dhall

in  pg.projectToJson { rules = [ wms.scalaSteward ] }
