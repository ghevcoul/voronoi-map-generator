package com.quantumgeranium.voronoi_mapper

import com.quantumgeranium.voronoi_mapper.util.Point

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class VoronoiDiagram {

  val random: Random = new Random()
  val xDimension: Int = 1000
  val yDimension: Int = 1000
  val centers: ArrayBuffer[Point] = initializeCenters()


  def writeDiagram(filename: String): Unit = {
    val writer: ImageWriter = new ImageWriter(xDimension, yDimension)

    // Draw the center points of each cell
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
