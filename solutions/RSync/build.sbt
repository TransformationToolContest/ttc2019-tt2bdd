import sbt.Keys.organization

val scrollVersion = "1.8"

mainClass in (Compile, run) := Some("MainApp")

lazy val ttcrsync = (project in file(".")).
  settings(
    name := "TTCRSyncApproach",
    organization := "de.tu-dresden.inf.st",
    version := "0.1",
    scalaVersion := "2.12.6",
    sbtVersion := "1.2.8",
    libraryDependencies ++= Seq(
        "com.github.max-leuthaeuser" %% "scroll" % scrollVersion)
  )
