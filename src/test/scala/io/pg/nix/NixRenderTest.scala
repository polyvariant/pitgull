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

  test("select on symbol") {
    expect(Nix.Select("builtins", "fetchurl").render == "builtins.fetchurl")
  }

  test("apply") {
    expect(Nix.Name("function").applied(Nix.Name("myurl")).render == "function myurl")
  }

  // these are probably the other way around
  test("left associated apply") {
    expect(Nix.Name("f").applied(Nix.Name("arg1")).applied(Nix.Name("arg2")).render == "f arg1 arg2")
  }

  test("right associated apply") {
    expect(Nix.Name("fun1").applied(Nix.Name("fun2").applied(Nix.Name("arg"))).render == "fun1 (fun2 arg)")
  }

  test("symbol application with object") {
    expect(Nix.Name("function").applied(Nix.obj("k" := Nix.Name("v"))).render == "function { k = v; }")
  }

  test("import path") {
    expect(Nix.Path(Paths.get("./local")).imported.render == "import ./local")
  }

  test("left associated import function application") {
    expect(Nix.Name("function").applied(Nix.Name("arg")).imported.render == "import (function arg)")
  }

  test("right associated import function application") {
    expect(Nix.Name("importee").imported.applied(Nix.Name("arg")).render == "(import importee) arg")
  }

  test("nested import") {
    expect(Nix.Name("path").imported.imported.render == "import (import path)")
  }

}
