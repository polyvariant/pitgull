let
  pg = import ./pitgull.nix;
  semver = level: pg.description.matches ".*labels:.*semver-${level}.*";
  patchUpdate = (semver "patch");
  wmsLibraryMinorUpdate = pg.allOf [
    (semver "minor")
    pg.description.matches
    ".*((com\.ocado\.ospnow\.wms)|(com\.ocado\.gm\.wms)).*"
  ];
in
pg.allOf [
  (pg.author.equals "scala_steward")
  (pg.status.equals pg.status.success)
  (pg.description.matches ".+world")
  (
    pg.oneOf [
      patchUpdate
      wmsLibraryMinorUpdate
    ]
  )
]
