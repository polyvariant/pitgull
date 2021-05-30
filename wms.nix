let
  pg = import (
    builtins.fetchurl {
      url = "https://raw.githubusercontent.com/polyvariant/pitgull/b51a6a3224af8a1f3e8c7242ddbfd0660a26cc7b/pitgull.nix";
      sha256 = "0rwszx3w37i9ljw49ld1ismfc6ip78n80fdk4c0nvka1hbbavf4p";
    }
    # ./pitgull.nix
  );
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
  (
    pg.anyOf [
      patchUpdate
      wmsLibraryMinorUpdate
    ]
  )
]
