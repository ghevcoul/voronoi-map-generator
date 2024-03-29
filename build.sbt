
organization := "com.quantumgeranium"
name := "voronoi-mapper"
version := "1.0.0-SNAPSHOT"

scalaVersion := "3.1.2"

libraryDependencies += "org.la4j" % "la4j" % "0.6.0"
libraryDependencies += ("io.jvm.uuid" %% "scala-uuid" % "0.3.1").cross(CrossVersion.for3Use2_13)
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.12" % "test"

// sbt-assembly options
assembly / assemblyJarName := "voronoi-mapper.jar"
assembly / mainClass := Some("com.quantumgeranium.voronoi_mapper.VoronoiMapper")
