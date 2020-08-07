ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / organization := "com.example"

val scalaTest = "org.scalatest" %% "scalatest" % "3.1.0"

lazy val hello = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "Programming in Scala 2nd edition",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
    libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  )

scalacOptions := Seq(
  "-unchecked", 
  "-deprecation",
  "-feature"
  // "-Xprint:typer"
  )

cancelable in Global := true