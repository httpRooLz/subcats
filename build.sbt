val testLibraries = List(
  "org.scalacheck" %% "scalacheck" % "1.13.2" % "test",
  "org.typelevel" %% "discipline" % "0.7" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test")

val catsLibraries = List(
  "org.typelevel" %% "algebra" % "0.5.1",
  "org.typelevel" %% "cats" % "0.7.2")

val simulacrumLibrary = List(
  "com.github.mpilquist" %% "simulacrum" % "0.8.0")

lazy val commonSettings = List(
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.8.0"),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  organization := "com.alexknvl",
  version := "0.2-SNAPSHOT",
  scalaOrganization := "org.typelevel",
  scalaVersion := "2.11.8",
  scalacOptions ++= List(
    "-deprecation", "-unchecked", "-feature",
    "-encoding", "UTF-8",
    "-language:existentials", "-language:higherKinds", "-language:implicitConversions",
    "-Yno-adapted-args", "-Ywarn-dead-code",
    "-Ywarn-numeric-widen", "-Xfuture",
    "-Ypartial-unification", "-Yliteral-types"),
  resolvers ++= List(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases")),
  libraryDependencies ++= testLibraries,
  wartremoverWarnings ++= Warts.all)

lazy val core = (project in file("core")).
  settings(name := "subcats-core").
  settings(commonSettings: _*).
  settings(libraryDependencies ++=
    catsLibraries ++
    simulacrumLibrary)

lazy val example = (project in file("example")).
  settings(name := "subcats-example").
  settings(commonSettings: _*).
  dependsOn(core)

lazy val root = (project in file(".")).
  settings(name := "subcats").
  settings(commonSettings: _*).
  aggregate(
    core,
    example)
