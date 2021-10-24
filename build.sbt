import sbt._
import Versions._

name := "stockbot"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("com.neomata.stockbot")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaActorsVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % akkaActorsVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.lihaoyi" %% "requests" % requestsVersion,
  "com.squareup.okhttp3" % "okhttp" % okhttpVersion,
  "com.squareup.retrofit2" % "retrofit" % retrofitVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
  "commons-net" % "commons-net" % commonsVersion,
  "com.sun.mail" % "javax.mail" % javaxMailVersion,
)
