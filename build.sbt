name := "spell"
scalaVersion := "2.12.1"

lazy val commonSettings = Seq(
  organization := "lk.pituwa",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.1",
  libraryDependencies := Seq(
    "com.typesafe.akka" %% "akka-http" % "10.0.9",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.9" % Test,
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.9",
    "com.typesafe.akka" %% "akka-actor" % "2.5.3",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.3" % Test,
    "com.typesafe.akka" %% "akka-stream" % "2.5.3",
    "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.3" % Test,
    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  ),
  resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases",
  resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
)

lazy val crawler = project.
  settings(commonSettings).
  settings(libraryDependencies ++= Seq(
    "org.scalaj" %% "scalaj-http" % "2.3.0",
    "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
    "io.lemonlabs" %% "scala-uri" % "0.4.16"
  )).
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
  ))


lazy val frontend = project
  .settings(
    commonSettings
  ).
  settings(libraryDependencies ++= Seq(

  )).
  dependsOn(crawler)

lazy val utils = project
  .settings(commonSettings).
  settings(libraryDependencies ++= Seq(
    "net.arnx" % "jsonic" % "1.3.3"
  ))

