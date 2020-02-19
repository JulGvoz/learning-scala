ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / organization := "com.example"

val scalaTest = "org.scalatest" %% "scalatest" % "3.1.0"

lazy val hello = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "Programming in Scala 2nd edition",
    libraryDependencies += scalaTest % Test,
  )

scalacOptions := Seq("-unchecked", "-deprecation")