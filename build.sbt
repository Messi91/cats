name := "cats"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.11"

crossScalaVersions := Seq("2.10.4", "2.11.2")

libraryDependencies += "org.typelevel" % "cats-core_2.11" % "1.0.0-MF"

initialCommands := "import example._"

scalacOptions += "-Ypartial-unification"
