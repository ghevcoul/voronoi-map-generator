package com.quantumgeranium.voronoi_mapper

import com.quantumgeranium.voronoi_mapper.triangulation.DelaunayTriangulation
import com.quantumgeranium.voronoi_mapper.util.{Cell, Line, Point}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class VoronoiDiagram {

  val random: Random = new Random()
  val xDimension: Int = 1000
  val yDimension: Int = 1000

  // Essential components of the Voronoi diagram
  val centers: ArrayBuffer[Point] = initializeCenters(10)
  val edges: ArrayBuffer[Line] = new ArrayBuffer[Line]()
  val cells: ArrayBuffer[Cell] = new ArrayBuffer[Cell]()

  // Counter for the cell ID
  var cellID = 0

  // Generate the Voronoi diagram, using the Delaunay triangulation method
  def generate(): Unit = {
    val delaunay = new DelaunayTriangulation(xDimension, yDimension)
    centers.foreach(c => {
      delaunay.addPoint(c)
    })
    
    delaunay.drawTriangulation("delaunay_triangulation.png")
  }

  def writeDiagram(filename: String): Unit = {
    val writer: ImageWriter = new ImageWriter(xDimension, yDimension)

    // Draw the center points of each cell
    writer.setColor("black")
    centers.foreach(c => writer.drawPoint(c))

    writer.writeImage(filename)
  }

  private def initializeCenters(numPoints: Int = 100): ArrayBuffer[Point] = {
    val points = new ArrayBuffer[Point]()
    for (_ <- 0 until numPoints) {
      points += new Point(random.nextDouble() * xDimension, random.nextDouble() * yDimension)
    }
    points
  }

}
