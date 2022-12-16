import com.typesafe.sbt.packager.docker.Cmd

import com.typesafe.sbt.packager.docker.ExecCmd

Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(
  List(
    organization := "io.pg",
    homepage := Some(url("https://github.com/pitgull/pitgull")),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
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

val Scala3 = "3.1.2"
ThisBuild / scalaVersion := Scala3
ThisBuild / crossScalaVersions := Seq(Scala3)
ThisBuild / githubWorkflowJavaVersions := Seq(GraalVM11)

ThisBuild / githubWorkflowPublishTargetBranches := Seq(
  RefPredicate.StartsWith(Ref.Branch("main")),
  RefPredicate.StartsWith(Ref.Tag("main"))
)

ThisBuild / githubWorkflowPublishPreamble := Seq(
  WorkflowStep.Use(
    ref = UseRef.Public("docker", "login-action", "v1"),
    params = Map(
      "username" -> "kubukoz",
      "password" -> "${{ secrets.DOCKER_HUB_TOKEN }}"
    )
  )
)

ThisBuild / githubWorkflowPublish := Seq(WorkflowStep.Sbt(List("docker:publish")))

// todo: reenable missinglink
ThisBuild / githubWorkflowBuild := List(
  WorkflowStep.Sbt(List("test", "missinglinkCheck"))
)

Test / fork := true

ThisBuild / missinglinkExcludedDependencies += moduleFilter(
  organization = "org.slf4j",
  name = "slf4j-api"
)

ThisBuild / libraryDependencySchemes ++= Seq(
  "io.circe" %% "circe-core" % "early-semver",
  "io.circe" %% "circe-generic-extras" % "early-semver",
  "io.circe" %% "circe-literal" % "early-semver",
  "io.circe" %% "circe-parser" % "early-semver"
)

def crossPlugin(x: sbt.librarymanagement.ModuleID) =
  compilerPlugin(x.cross(CrossVersion.full))

val compilerPlugins = List(
  crossPlugin("org.polyvariant" % "better-tostring" % "0.3.15")
)

val commonSettings = List(
  scalacOptions --= List("-Xfatal-warnings"),
  libraryDependencies ++= List(
    "org.typelevel" %% "cats-core" % "2.7.0",
    "org.typelevel" %% "cats-effect" % "3.3.14",
    "co.fs2" %% "fs2-core" % "3.2.14",
    "com.github.valskalla" %% "odin-core" % "0.13.0",
    "io.circe" %% "circe-core" % "0.14.2",
    "dev.optics" %% "monocle-core" % "3.1.0",
    "com.disneystreaming" %% "weaver-cats" % "0.7.13" % Test,
    "com.disneystreaming" %% "weaver-scalacheck" % "0.7.13" % Test
  ) ++ compilerPlugins,
  testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
  publish / skip := true
)

lazy val gitlab = project
  .settings(
    commonSettings,
    libraryDependencies ++= List(
      "is.cir" %% "ciris" % "2.3.3",
      "com.kubukoz" %% "caliban-gitlab" % "0.1.0",
      "io.circe" %% "circe-parser" % "0.14.2" % Test,
      "io.circe" %% "circe-literal" % "0.14.2" % Test,
      "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.18.0-M17",
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "0.18.0-M17",
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % "0.18.0-M17"
    )
  )
  .dependsOn(core)

lazy val bootstrap = project
  .settings(
    scalaVersion := Scala3,
    libraryDependencies ++= List(
      "org.typelevel" %% "cats-core" % "2.7.0",
      "org.typelevel" %% "cats-effect" % "3.3.14",
      "com.kubukoz" %% "caliban-gitlab" % "0.1.0",
      "com.softwaremill.sttp.client3" %% "core" % "3.3.18",
      "com.softwaremill.sttp.client3" %% "circe" % "3.3.18",
      "io.circe" %% "circe-core" % "0.14.2",
      crossPlugin("org.polyvariant" % "better-tostring" % "0.3.15")
    ),
    publish / skip := true,
    githubWorkflowArtifactUpload := false,
    nativeImageVersion := "22.1.0",
    nativeImageOptions ++= Seq(
      s"-H:ReflectionConfigurationFiles=${(Compile / resourceDirectory).value / "reflect-config.json"}",
      "--enable-url-protocols=https",
      "-H:+ReportExceptionStackTraces",
      "--no-fallback"
    )
  )
  .enablePlugins(NativeImagePlugin)

ThisBuild / githubWorkflowBuild ++= Seq(
  WorkflowStep.Run(
    List("sbt bootstrap/nativeImage"),
    name = Some("Build native image")
  ),
  WorkflowStep.Use(
    UseRef.Public("actions", "upload-artifact", "v2"),
    name = Some(s"Upload binary"),
    params = Map(
      "name" -> s"pitgull-bootstrap-$${{ matrix.os }}",
      "path" -> "./bootstrap/target/native-image/bootstrap"
    )
  )
)

lazy val core = project.settings(commonSettings).settings(name += "-core")

//workaround for docker not accepting + (the default separator in sbt-dynver)
ThisBuild / dynverSeparator := "-"

val installDhallJson =
  ExecCmd(
    "RUN",
    "sh",
    "-c",
    "curl -L https://github.com/dhall-lang/dhall-haskell/releases/download/1.34.0/dhall-json-1.7.1-x86_64-linux.tar.bz2 | tar -vxj -C /"
  )

lazy val pitgull =
  project
    .in(file("."))
    .enablePlugins(BuildInfoPlugin, DockerPlugin, JavaAppPackaging)
    .settings(commonSettings)
    .settings(
      name := "pitgull",
      dockerBaseImage := "adoptopenjdk/openjdk11:jre-11.0.8_10",
      // dockerCommands += ExecCmd(
      //   "RUN",
      //   "sh",
      //   "-c",
      //   "apk update && apk add curl bash"
      // ),
      // dockerCommands ++=
      //   Cmd("USER", "root") :: ExecCmd(
      //     "RUN",
      //     "sh",
      //     "-c",
      //     "apk update && apk add curl bash"
      //   ) :: installDhallJson :: Nil,
      Docker / packageName := "kubukoz/pitgull",
      Docker / mappings +=
        file("./example.dhall") -> "/opt/docker/example.dhall",
      mainClass := Some("io.pg.ProjectConfigReader"),
      buildInfoOptions += BuildInfoOption.ConstantValue,
      buildInfoPackage := "io.pg",
      buildInfoKeys := List(version, scalaVersion),
      libraryDependencies ++= List(
        "com.softwaremill.sttp.client3" %% "http4s-backend" % "3.3.18",
        "org.http4s" %% "http4s-dsl" % "0.23.11",
        "org.http4s" %% "http4s-circe" % "0.23.11",
        "org.http4s" %% "http4s-blaze-server" % "0.23.13",
        "org.http4s" %% "http4s-blaze-client" % "0.23.13",
        "is.cir" %% "ciris" % "2.3.3",
        "io.chrisdavenport" %% "cats-time" % "0.4.0",
        "com.github.valskalla" %% "odin-core" % "0.13.0",
        "com.github.valskalla" %% "odin-slf4j" % "0.13.0",
        "io.github.vigoo" %% "prox-fs2-3" % "0.7.7",
        "io.circe" %% "circe-literal" % "0.14.2" % Test
      )
    )
    .dependsOn(core, gitlab)
    .aggregate(core, gitlab, bootstrap)
