name := "spell"
scalaVersion := "2.12.1"

lazy val commonSettings = Seq(
  organization := "lk.pituwa",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.1")

lazy val crawler = project.
  settings(commonSettings).
  settings(libraryDependencies ++= Seq(
    "org.scalaj" %% "scalaj-http" % "2.3.0",
    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )).
  settings(resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases")

lazy val indexer = project
  .settings(
    commonSettings
  )