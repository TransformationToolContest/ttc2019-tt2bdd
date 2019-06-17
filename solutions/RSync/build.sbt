import sbt.Keys.organization

val scrollVersion = "1.8"
val scalaFxVersion = "8.0.144-R12"

mainClass in (Compile, run) := Some("MainApp")

lazy val modelmanagementprovider = (project in file(".")).
  settings(
    name := "ModelManagementProvider",
    organization := "de.tu-dresden.inf.st",
    version := "0.1",
    scalaVersion := "2.12.6",
    sbtVersion := "1.2.8",
    libraryDependencies ++= Seq(
        "com.github.max-leuthaeuser" %% "scroll" % scrollVersion,
        "org.scalafx" %% "scalafx" % scalaFxVersion)
  )
