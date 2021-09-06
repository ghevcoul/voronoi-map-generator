
organization := "com.quantumgeranium"
name := "voronoi-mapper"
version := "1.0.0-SNAPSHOT"

scalaVersion := "3.0.1"

libraryDependencies += "org.la4j" % "la4j" % "0.6.0"
libraryDependencies += ("io.jvm.uuid" %% "scala-uuid" % "0.3.1").cross(CrossVersion.for3Use2_13)

// sbt-assembly options
assemblyJarName in assembly := "voronoi-mapper.jar"
mainClass in assembly := Some("com.quantumgeranium.voronoi_mapper.VoronoiMapper")
