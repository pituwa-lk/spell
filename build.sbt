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
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
    "io.lemonlabs" %% "scala-uri" % "0.4.16",
    "com.typesafe.akka" %% "akka-actor" % "2.5.3",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.3" % Test
  )).
  settings(resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases").
  dependsOn(utils, indexer, models)

lazy val indexer = project
  .settings(
    commonSettings
  ).
  dependsOn(models, utils)

lazy val models = project
  .settings(
    commonSettings
  ).
  settings(libraryDependencies ++= Seq(
    "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
    "io.lemonlabs" %% "scala-uri" % "0.4.16"
  )).
  settings(resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases")


lazy val frontend = project
  .settings(
    commonSettings
  ).
  settings(libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % "10.0.9",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.9" % Test,
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.9"
  )).
  dependsOn(crawler)

lazy val utils = project
  .settings(commonSettings).
  settings(libraryDependencies ++= Seq(
    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "net.arnx" % "jsonic" % "1.3.3"
  )).
  settings(resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases").
  settings(resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases")

