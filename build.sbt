name := "specs2-essentials"

version := "0.1"

scalaVersion := "2.13.4"

libraryDependencies += "org.specs2" %% "specs2-core" % "4.10.0" % "test"

scalacOptions in Test ++= Seq("-Yrangepos")
