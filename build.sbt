ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val jarName = "zno.jar"

lazy val root = (project in file("."))
  .settings(
    name := "scala-quiz-manager",
    assembly / assemblyJarName := jarName
  )

lazy val createInstaller = taskKey[Unit]("Execute the shell script")

createInstaller := {
  import java.nio.file.{Files, Paths, StandardCopyOption}

  import scala.language.postfixOps
  import scala.sys.process._

  // create in/out dirs
  val installerIn  = Paths.get("./installer/in")
  val installerOut = Paths.get("./installer/out")

  if (Files.notExists(installerIn)) {
    Files.createDirectories(installerIn)
  }
  if (Files.notExists(installerOut)) {
    Files.createDirectories(installerOut)
  }

  // copy JAR
  Files.copy(
    Paths.get(s"./target/scala-2.13/$jarName"),
    Paths.get(s"$installerIn/$jarName"),
    StandardCopyOption.REPLACE_EXISTING
  )

  // copy dataset
  Files.copy(
    Paths.get("./src/main/resources/emph.txt"),
    Paths.get(s"$installerIn/data.txt"),
    StandardCopyOption.REPLACE_EXISTING
  )

  // run jpackage
  "powershell -c ./installer/create_installer.ps1" !
}

createInstaller := (createInstaller dependsOn assembly).value
