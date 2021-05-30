let
  pg = import ./pitgull.nix /* (
    builtins.fetchurl {
      url = "https://raw.githubusercontent.com/polyvariant/pitgull/1faf7a7c505e289895b16a481a4c156707f4c152/pitgull.nix";
      sha256 = "01ywdqqiy53mciz8m3y8ap56797dfy6v9bfrad54awprbs1qlx5j";
    }
  ) */;
  semver = level: pg.description.matches ".*labels:.*semver-${level}.*";
  patchUpdate = (semver "patch");
  wmsLibraryMinorUpdate = pg.allOf [
    (semver "minor")
    (pg.description.matches ".*((com\.ocado\.ospnow\.wms)|(com\.ocado\.gm\.wms)).*")
  ];
in
pg.allOf [
  (pg.author.equals "scala_steward")
  (pg.status.equals pg.status.success)
  (pg.description.matches ".+world")
  (
    pg.anyOf [
      patchUpdate
      wmsLibraryMinorUpdate
    ]
  )
]
