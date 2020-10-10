package com.quantumgeranium.voronoi_mapper

import com.quantumgeranium.voronoi_mapper.triangulation.DelaunayTriangulation
import com.quantumgeranium.voronoi_mapper.util.Point

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object VoronoiMapper {

  val xDimension = 1000
  val yDimension = 1000
  val numPoints = 100

  val random = new Random()

  def initializeCenters(): ArrayBuffer[Point] = {
    val writer = new ImageWriter(xDimension, yDimension)
    writer.setColor("black")
    val points = new ArrayBuffer[Point]()
    for (_ <- 0 until numPoints) {
      val p = new Point(random.nextDouble() * xDimension, random.nextDouble() * yDimension)
      writer.drawPoint(p)
      points.addOne(p)
    }
    writer.writeImage("center_points.png")
    points
  }

  def main(args: Array[String]): Unit = {

    val centers = initializeCenters()

    val delaunay = new DelaunayTriangulation(xDimension, yDimension)
    centers.foreach(c => {
      delaunay.addPoint(c)
    })
    delaunay.drawTriangulation("delaunay_triangulation.png")

    val graph = delaunay.convertToDualGraph()
    graph.drawGraph(xDimension, yDimension)
  }

}
