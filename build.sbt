
organization := "com.quantumgeranium"
name := "voronoi-mapper"
version := "1.0.0-SNAPSHOT"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.la4j" % "la4j" % "0.6.0",
  "io.jvm.uuid" %% "scala-uuid" % "0.3.1",
)

// sbt-assembly options
assemblyJarName in assembly := "voronoi-mapper.jar"
mainClass in assembly := Some("com.quantumgeranium.voronoi_mapper.VoronoiMapper")
