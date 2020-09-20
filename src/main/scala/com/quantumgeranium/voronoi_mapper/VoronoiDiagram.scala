package com.quantumgeranium.voronoi_mapper

import com.quantumgeranium.voronoi_mapper.util.{Cell, Line, Point}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class VoronoiDiagram {

  val random: Random = new Random()
  val xDimension: Int = 1000
  val yDimension: Int = 1000

  // Essential components of the Voronoi diagram
  val centers: ArrayBuffer[Point] = initializeCenters()
  val edges: ArrayBuffer[Line] = new ArrayBuffer[Line]()
  val cells: ArrayBuffer[Cell] = new ArrayBuffer[Cell]()

  // Counter for the cell ID
  var cellID = 0

  // Generate the Voronoi diagram, using the algorithm described at
  // https://courses.cs.washington.edu/courses/cse326/00wi/projects/voronoi.html
  def generate(): Unit = {
    createDummyCells()
  }

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

  private def createCellForCenter(center: Point): Cell = {
    val newCell = new Cell(center, cellID)

    for (c <- cells) {
      val connectingLine = new Line(center, c.center)
      val bisector = connectingLine.perpendicularBisector()

    }

    cellID += 1
    newCell
  }

  // Create a set of dummy cells to bound the diagram
  private def createDummyCells(): Unit = {
    val c1 = new Cell(new Point(-xDimension, -yDimension), -1)
    c1.addEdge(new Line(new Point(xDimension / 2, yDimension / 2), new Point(xDimension / 2, -10 * yDimension)))
    c1.addEdge(new Line(new Point(xDimension / 2, -10 * yDimension), new Point(-10 * xDimension, yDimension / 2)))
    c1.addEdge(new Line(new Point(-10 * xDimension, yDimension / 2), new Point(xDimension / 2, yDimension / 2)))
    cells += c1

    val c2 = new Cell(new Point(2*xDimension, -yDimension), -2)
    c2.addEdge(new Line(new Point(xDimension / 2, yDimension / 2), new Point(xDimension / 2, -10 * yDimension)))
    c2.addEdge(new Line(new Point(xDimension / 2, -10 * yDimension), new Point(10 * xDimension, yDimension / 2)))
    c2.addEdge(new Line(new Point(10 * xDimension, yDimension / 2), new Point(xDimension / 2, yDimension / 2)))
    cells += c2

    val c3 = new Cell(new Point(2*xDimension, 2*yDimension), -3)
    c3.addEdge(new Line(new Point(xDimension / 2, yDimension / 2), new Point(xDimension / 2, 10 * yDimension)))
    c3.addEdge(new Line(new Point(xDimension / 2, 10 * yDimension), new Point(10 * xDimension, yDimension / 2)))
    c3.addEdge(new Line(new Point(10 * xDimension, yDimension / 2), new Point(xDimension / 2, yDimension / 2)))
    cells += c3

    val c4 = new Cell(new Point(-xDimension, 2*yDimension), -4)
    c4.addEdge(new Line(new Point(xDimension / 2, yDimension / 2), new Point(xDimension / 2, 10 * yDimension)))
    c4.addEdge(new Line(new Point(xDimension / 2, 10 * yDimension), new Point(-10 * xDimension, yDimension / 2)))
    c4.addEdge(new Line(new Point(-10 * xDimension, yDimension / 2), new Point(xDimension / 2, yDimension / 2)))
    cells += c4
  }

}
