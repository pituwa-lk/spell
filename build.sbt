import sbt.dsl.enablePlugins

name := "spell"
scalaVersion := "2.12.1"

enablePlugins(UniversalPlugin)
enablePlugins(JavaServerAppPackaging)

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
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
    "com.typesafe" % "config" % "1.3.1",
    "ch.megard" %% "akka-http-cors" % "0.2.1",
    "org.apache.pdfbox" % "pdfbox" % "2.0.7"
  ),
  resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases",
  resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  resolvers += "Maven Main" at "http://central.maven.org/maven2/",
  mainClass in Compile := Some("lk.pituwa.capture.WebServer")
)

/*mappings in Universal <+= (packageBin in Compile) map { jar =>
  jar -> ("lib/" + jar.getName)
}*/

lazy val crawler = project.
  settings(commonSettings).
  settings(libraryDependencies ++= Seq(
    "org.scalaj" %% "scalaj-http" % "2.3.0",
    "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
    "io.lemonlabs" %% "scala-uri" % "0.4.16"
  )).
  dependsOn(utils, models).enablePlugins(JavaServerAppPackaging).enablePlugins(JavaAppPackaging)

lazy val models = project
  .settings(
    commonSettings
  ).
  settings(libraryDependencies ++= Seq(
    "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
    "io.lemonlabs" %% "scala-uri" % "0.4.16"
  ))

lazy val utils = project
  .settings(commonSettings).
  settings(libraryDependencies ++= Seq(
    "net.arnx" % "jsonic" % "1.3.3",
    /*"com.typesafe.slick" %% "slick" % "3.2.1",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",*/
    "com.h2database" % "h2" % "1.4.196"
  )).
  dependsOn(models)


lazy val Spell = Project(id = "Spelling", base = file("."),
    settings = globalSettings ++ commonSettings).aggregate(models, utils, crawler).dependsOn(models, utils, crawler).enablePlugins(JavaAppPackaging)