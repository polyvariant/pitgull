let
  pg = import (
    builtins.fetchurl {
      url = "https://raw.githubusercontent.com/polyvariant/pitgull/60736f1588f2ea45b544e48f18d7685a91885f8c/pitgull.nix";
      sha256 = "1hr5qd3wfkax33m52l49clagxg55jix4qx3ilpqyzyjsld5zyhv9";
    }
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
