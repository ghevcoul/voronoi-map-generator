package com.quantumgeranium.voronoi_mapper

import com.quantumgeranium.voronoi_mapper.triangulation.DelaunayTriangulation
import com.quantumgeranium.voronoi_mapper.util.Point

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class VoronoiDiagram {

  val random: Random = new Random()
  val xDimension: Int = 1000
  val yDimension: Int = 1000

  // Essential components of the Voronoi diagram
  val centers: ArrayBuffer[Point] = initializeCenters()

  // Generate the Voronoi diagram, using the Delaunay triangulation method
  def generate(): Unit = {
    val delaunay = new DelaunayTriangulation(xDimension, yDimension)
    centers.foreach(c => {
      delaunay.addPoint(c)
    })
    delaunay.drawTriangulation("delaunay_triangulation.png")

    val graph = delaunay.convertToDualGraph()
    graph.drawGraph(xDimension, yDimension)
  }

  private def initializeCenters(numPoints: Int = 100): ArrayBuffer[Point] = {
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

}
