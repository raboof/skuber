resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

val playws = "com.typesafe.play" %% "play-ws" % "2.4.3"
val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.12.4"
val specs2 = "org.specs2" %% "specs2-core" % "3.6.2"
val snakeYaml =  "org.yaml" % "snakeyaml" % "1.16"
val commonsIO = "commons-io" % "commons-io" % "2.4"
val playIterateesExtra = "com.typesafe.play.extras" %% "iteratees-extras" % "1.5.0"

// Akka is required by the examples 
val akka ="com.typesafe.akka" %% "akka-actor" % "2.4.0"

// Need Java 8 or later as the java.time package is used to represent K8S timestamps
scalacOptions += "-target:jvm-1.8"

scalacOptions in Test ++= Seq("-Yrangepos")

lazy val commonSettings = Seq(
  organization := "io.doriordan",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

lazy val skuberSettings = Seq(
  name := "skuber-client",
  libraryDependencies ++= Seq(playws,playIterateesExtra,snakeYaml,commonsIO,scalaCheck % Test,specs2 % Test).
				map(_.exclude("commons-logging","commons-logging"))
)

lazy val examplesSettings = Seq(
  name := "skuber-examples",
  libraryDependencies += akka
)

lazy val root = (project in file(".")) aggregate(
  client,
  examples)

lazy val client = (project in file("client")).
  settings(commonSettings: _*).
  settings(skuberSettings: _*)

lazy val examples = (project in file("examples")).
  settings(commonSettings: _*).
  settings(examplesSettings: _*).
  dependsOn(client)

