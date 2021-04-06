import com.typesafe.sbt.packager.docker.ExecCmd
import com.typesafe.sbt.packager.docker.Cmd

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

val Scala213 = "2.13.5"
ThisBuild / scalaVersion := Scala213
ThisBuild / crossScalaVersions := Seq(Scala213)
ThisBuild / githubWorkflowJavaVersions := Seq(GraalVM11)
ThisBuild / githubWorkflowPublishTargetBranches := Nil

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

def crossPlugin(x: sbt.librarymanagement.ModuleID) =
  compilerPlugin(x.cross(CrossVersion.full))

val compilerPlugins = List(
  crossPlugin("org.typelevel" % "kind-projector" % "0.11.3"),
  crossPlugin("com.github.cb372" % "scala-typed-holes" % "0.1.8"),
  crossPlugin("com.kubukoz" % "better-tostring" % "0.2.8"),
  compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
)

val commonSettings = List(
  scalacOptions --= List("-Xfatal-warnings"),
  scalacOptions += "-Ymacro-annotations",
  libraryDependencies ++= List(
    "org.typelevel" %% "cats-core" % "2.5.0",
    "org.typelevel" %% "cats-effect" % "2.4.1",
    "org.typelevel" %% "cats-tagless-macros" % "0.12",
    "co.fs2" %% "fs2-core" % "2.5.4",
    "com.github.valskalla" %% "odin-core" % "0.11.0",
    "io.circe" %% "circe-core" % "0.13.0",
    "com.github.julien-truffaut" %% "monocle-macro" % "2.1.0",
    "com.disneystreaming" %% "weaver-framework" % "0.7.0" % Test,
    "com.disneystreaming" %% "weaver-scalacheck" % "0.7.0" % Test
  ) ++ compilerPlugins,
  testFrameworks += new TestFramework("weaver.framework.TestFramework"),
  publish / skip := true
)

lazy val gitlab = project
  .settings(
    commonSettings,
    libraryDependencies ++= List(
      "is.cir" %% "ciris" % "1.2.1",
      "com.kubukoz" %% "caliban-gitlab" % "0.0.14",
      "io.circe" %% "circe-generic-extras" % "0.13.0",
      "io.circe" %% "circe-parser" % "0.13.0" % Test,
      "io.circe" %% "circe-literal" % "0.13.0" % Test,
      "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.17.19",
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "0.17.19",
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % "0.17.19"
    )
  )
  .dependsOn(core)

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
      Docker / mappings += (
        file("./example.dhall") -> "/opt/docker/example.dhall"
      ),
      mainClass := Some("io.pg.ProjectConfigReader"),
      buildInfoPackage := "io.pg",
      buildInfoKeys := List(version, scalaVersion),
      libraryDependencies ++= List(
        "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % "0.17.19",
        "com.softwaremill.sttp.client3" %% "http4s-backend" % "3.2.3",
        "org.http4s" %% "http4s-blaze-server" % "0.21.21",
        "org.http4s" %% "http4s-blaze-client" % "0.21.21",
        "is.cir" %% "ciris" % "1.2.1",
        "io.circe" %% "circe-generic-extras" % "0.13.0",
        "io.estatico" %% "newtype" % "0.4.4",
        "io.scalaland" %% "chimney" % "0.6.1",
        "io.chrisdavenport" %% "cats-time" % "0.3.4",
        "com.github.valskalla" %% "odin-core" % "0.11.0",
        "com.github.valskalla" %% "odin-slf4j" % "0.11.0",
        "io.github.vigoo" %% "prox" % "0.5.2"
      )
    )
    .dependsOn(core, gitlab)
    .aggregate(core, gitlab)
