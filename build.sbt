inThisBuild(
  List(
    organization := "io.pg",
    homepage := Some(url("https://github.com/pitgull/pitgull")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "kubukoz",
        "Jakub Kozłowski",
        "kubukoz@gmail.com",
        url("https://blog.kubukoz.com")
      )
    )
  )
)

val GraalVM11 = "graalvm11@20.1.0"

ThisBuild / crossScalaVersions := Seq(Scala213)
ThisBuild / githubWorkflowJavaVersions := Seq(GraalVM11)
ThisBuild / githubWorkflowPublishTargetBranches := Nil

ThisBuild / githubWorkflowBuild := List(WorkflowStep.Sbt(List("test", "missinglinkCheck")))

missinglinkExcludedDependencies in ThisBuild += moduleFilter(organization = "ch.qos.logback", name = "logback-classic")
missinglinkExcludedDependencies in ThisBuild += moduleFilter(organization = "ch.qos.logback", name = "logback-core")

def crossPlugin(x: sbt.librarymanagement.ModuleID) = compilerPlugin(x.cross(CrossVersion.full))

val compilerPlugins = List(
  crossPlugin("org.typelevel" % "kind-projector" % "0.11.0"),
  crossPlugin("com.github.cb372" % "scala-typed-holes" % "0.1.5"),
  crossPlugin("com.kubukoz" % "better-tostring" % "0.2.4"),
  compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
)

val Scala213 = "2.13.3"

val commonSettings = List(
  name := "pitgull",
  scalaVersion := Scala213,
  scalacOptions --= List("-Xfatal-warnings"),
  scalacOptions += "-Ymacro-annotations",
  scalacOptions += "-Yimports:scala,scala.Predef,java.lang,cats",
  libraryDependencies ++= List(
    "org.typelevel" %% "cats-effect" % "2.1.4",
    "org.scalatest" %% "scalatest" % "3.2.0" % Test //todo: munit
  ) ++ compilerPlugins
)

val core = project.settings(commonSettings).settings(name += "-core")

val pitgull =
  project
    .in(file("."))
    .enablePlugins(BuildInfoPlugin)
    .settings(commonSettings)
    .settings(
      skip in publish := true,
      buildInfoPackage := "io.pg",
      buildInfoKeys := List(version, scalaVersion),
      libraryDependencies ++= List(
        "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.16.9",
        "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "0.16.9",
        "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % "0.16.9",
        "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % "0.16.9",
        "com.softwaremill.sttp.client" %% "circe" % "2.2.3",
        "com.softwaremill.sttp.client" %% "http4s-backend" % "2.2.3",
        "org.http4s" %% "http4s-blaze-server" % "0.21.6",
        "is.cir" %% "ciris" % "1.1.1",
        "io.circe" %% "circe-generic-extras" % "0.13.0",
        "io.estatico" %% "newtype" % "0.4.4",
        "io.scalaland" %% "chimney" % "0.5.3",
        "org.typelevel" %% "cats-tagless-macros" % "0.11",
        "org.typelevel" %% "cats-mtl-core" % "0.7.1",
        "com.olegpy" %% "meow-mtl-effects" % "0.4.0",
        "com.olegpy" %% "meow-mtl-core" % "0.4.0",
        "io.chrisdavenport" %% "cats-time" % "0.3.0",
        "com.github.valskalla" %% "odin-core" % "0.7.0",
        "ch.qos.logback" % "logback-classic" % "1.2.3"
      )
    )
    .dependsOn(core)
    .aggregate(core)
