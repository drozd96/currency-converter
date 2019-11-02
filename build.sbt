name := "currency-converter"

version := "0.1"

scalaVersion := "2.11.11"

val vAkkaHttp = "10.1.7"

libraryDependencies ++=
  Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test, 
      "com.typesafe.akka" %% "akka-http" % vAkkaHttp,
      "com.typesafe.akka" %% "akka-http-core" % vAkkaHttp,
      "com.typesafe.akka" %% "akka-http-spray-json" % vAkkaHttp,
      "com.typesafe.akka" %% "akka-http-testkit" % vAkkaHttp,
      "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.23"
  )