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
  name := "pitgull",
  updateOptions := updateOptions.value.withGigahorse(false),
  libraryDependencies ++= List(
    "org.scalatest" %% "scalatest" % "3.1.0" % Test
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
        "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.16.7",
        "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "0.16.7",
        "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % "0.16.7",
        "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % "0.16.7",
        "com.softwaremill.sttp.client" %% "circe" % "2.2.3",
        "com.softwaremill.sttp.client" %% "http4s-backend" % "2.2.3",
        "org.http4s" %% "http4s-blaze-server" % "0.21.6",
        "is.cir" %% "ciris" % "1.1.1",
        "ch.qos.logback" % "logback-classic" % "1.2.3"
      )
    )
    .dependsOn(core)
    .aggregate(core)
