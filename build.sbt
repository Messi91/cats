name := "cats"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.10.4", "2.11.2")

libraryDependencies += "com.softwaremill.macwire" % "macros_2.11" % "2.3.0" % "test"

initialCommands := "import example._"
