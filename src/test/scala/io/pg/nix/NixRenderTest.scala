package io.pg.nix

import Nix.syntax._
import weaver._
import java.nio.file.Paths

object NixRenderTest extends SimpleIOSuite {
  test("single line string") {
    expect(Nix.Str("foo").render == "\"foo\"")
  }

  test("multiline string") {
    expect(Nix.Str("foo\nbar").render == "''foo\nbar''")
  }

  test("empty record") {
    expect(Nix.Record(Map.empty).render == "{ }")
  }

  test("single-element record") {
    expect(Nix.Record(Map(RecordEntry("key") -> "value".toNix)).render == """{ key = "value"; }""")
  }

  test("multi-elem record") {
    val input = Nix.Record(Map(RecordEntry("key") -> "value".toNix, RecordEntry("key2") -> "value2".toNix))

    expect(input.render == """{ key = "value"; key2 = "value2"; }""")
  }

  test("nested record") {
    val input =
      Nix.Record(Map(RecordEntry("obj") -> Nix.Record(Map(RecordEntry("nested") -> "value2".toNix))))

    expect(input.render == """{ obj = { nested = "value2"; }; }""")
  }

  test("relative path: .") {
    expect(Nix.Path(Paths.get(".")).render == "./.")
  }

  test("relative path: ..") {
    expect(Nix.Path(Paths.get("..")).render == "./..")
  }

  test("relative path: file in directory") {
    expect(Nix.Path(Paths.get("file")).render == "./file")
  }

  test("absolute path") {
    expect(Nix.Path(Paths.get("/file")).render == "/file")
  }
}
