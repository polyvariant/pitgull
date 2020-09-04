import com.typesafe.sbt.packager.docker.ExecCmd
import com.typesafe.sbt.packager.docker.Cmd

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

val GraalVM11 = "graalvm-ce-java11@20.1.0"

ThisBuild / crossScalaVersions := Seq(Scala213)
ThisBuild / githubWorkflowJavaVersions := Seq(GraalVM11)
ThisBuild / githubWorkflowPublishTargetBranches := Nil

ThisBuild / githubWorkflowBuild := List(WorkflowStep.Sbt(List("test", "missinglinkCheck")))

Test / fork := true

missinglinkExcludedDependencies in ThisBuild += moduleFilter(organization = "org.slf4j", name = "slf4j-api")

def crossPlugin(x: sbt.librarymanagement.ModuleID) = compilerPlugin(x.cross(CrossVersion.full))

val compilerPlugins = List(
  crossPlugin("org.typelevel" % "kind-projector" % "0.11.0"),
  crossPlugin("com.github.cb372" % "scala-typed-holes" % "0.1.5"),
  crossPlugin("com.kubukoz" % "better-tostring" % "0.2.4"),
  compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
)

val Scala213 = "2.13.3"

val commonSettings = List(
  scalaVersion := Scala213,
  scalacOptions --= List("-Xfatal-warnings"),
  scalacOptions += "-Ymacro-annotations",
  libraryDependencies ++= List(
    "org.typelevel" %% "cats-effect" % "2.1.4",
    "org.typelevel" %% "cats-tagless-macros" % "0.11",
    "co.fs2" %% "fs2-core" % "2.4.4",
    "io.circe" %% "circe-core" % "0.13.0",
    "org.scalatest" %% "scalatest" % "3.2.2" % Test //todo: munit
  ) ++ compilerPlugins
)

val gitlab = project
  .settings(
    commonSettings,
    libraryDependencies ++= List(
      "is.cir" %% "ciris" % "1.2.1",
      "com.kubukoz" %% "caliban-gitlab" % "0.0.2",
      "io.circe" %% "circe-generic-extras" % "0.13.0",
      "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.16.9",
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "0.16.9",
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % "0.16.9"
    )
  )

val core = project.settings(commonSettings).settings(name += "-core")

//temporary workaround for docker not accepting sbt-dynver's insanely specific versions as tags
ThisBuild / version := "0.0.0"

val installDhallJson = List(
  ExecCmd(
    "RUN",
    "sh",
    "-c",
    "curl -L https://github.com/dhall-lang/dhall-haskell/releases/download/1.34.0/dhall-json-1.7.1-x86_64-linux.tar.bz2 | tar -vxj -C /"
  )
)

val pitgull =
  project
    .in(file("."))
    .enablePlugins(BuildInfoPlugin, DockerPlugin, JavaAppPackaging)
    .settings(commonSettings)
    .settings(
      name := "pitgull",
      dockerBaseImage := "adoptopenjdk/openjdk11:jre-11.0.8_10-alpine",
      dockerCommands ++=
        Cmd("USER", "root") :: ExecCmd("RUN", "sh", "-c", "apk update && apk add curl bash") :: installDhallJson,
      Docker / packageName := "kubukoz/pitgull",
      mainClass := Some("io.pg.ProjectConfigReader"),
      skip in publish := true,
      buildInfoPackage := "io.pg",
      buildInfoKeys := List(version, scalaVersion),
      libraryDependencies ++= List(
        "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.16.16",
        "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "0.16.16",
        "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % "0.16.16",
        "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % "0.16.16",
        "com.softwaremill.sttp.client" %% "circe" % "2.2.7",
        "com.softwaremill.sttp.client" %% "http4s-backend" % "2.2.7",
        "org.http4s" %% "http4s-blaze-server" % "0.21.7",
        "org.http4s" %% "http4s-blaze-client" % "0.21.7",
        "is.cir" %% "ciris" % "1.2.1",
        "io.circe" %% "circe-generic-extras" % "0.13.0",
        "io.estatico" %% "newtype" % "0.4.4",
        "io.scalaland" %% "chimney" % "0.5.3",
        "org.typelevel" %% "cats-mtl-core" % "0.7.1",
        "com.olegpy" %% "meow-mtl-effects" % "0.4.1",
        "com.olegpy" %% "meow-mtl-core" % "0.4.1",
        "io.chrisdavenport" %% "cats-time" % "0.3.4",
        "com.github.valskalla" %% "odin-core" % "0.8.1",
        "com.github.valskalla" %% "odin-slf4j" % "0.8.1",
        "io.github.vigoo" %% "prox" % "0.5.2"
      )
    )
    .dependsOn(core, gitlab)
    .aggregate(core)
