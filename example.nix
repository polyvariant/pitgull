let
  pg = import ./pitgull.nix;
in
pg.allOf [
  (pg.status.equals pg.status.success)
  (
    pg.anyOf [
      (pg.author.equals "user1@gmail.com")
      (pg.author.equals "user2@gmail.com")
    ]
  )
  (pg.description.matches ".+world")
]
