
organization := "com.quantumgeranium"
name := "voronoi-mapper"
version := "1.0.0-SNAPSHOT"

scalaVersion := "2.13.3"

// sbt-assembly options
assemblyJarName in assembly := "voronoi-mapper.jar"
mainClass in assembly := Some("com.quantumgeranium.voronoi_mapper.VoronoiMapper")
