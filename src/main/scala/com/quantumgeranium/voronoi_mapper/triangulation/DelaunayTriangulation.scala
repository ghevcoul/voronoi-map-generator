package com.quantumgeranium.voronoi_mapper.triangulation

import com.quantumgeranium.voronoi_mapper.ImageWriter
import com.quantumgeranium.voronoi_mapper.util.Point
import io.jvm.uuid._

import scala.collection.mutable

class DelaunayTriangulation(val xDimension: Int, val yDimension: Int) {

  val vertices: mutable.Map[UUID, Point] = mutable.Map()
  val triangles: mutable.ArrayBuffer[Triangle] = mutable.ArrayBuffer[Triangle]()
  addSuperTriangle()

  val centers: mutable.Map[UUID, Point] = mutable.Map()

  //TODO: After done adding points, remove superTriangle from data structures

  //TODO: Convert to DualGraph representation

  def addPoint(newPoint: Point): Unit = {
    val pointID = UUID.random
    vertices += (pointID -> newPoint)
    // Check if the new point is inside the circumcircle of any existing triangle
    val badTriangles = mutable.Set[Triangle]()
    triangles.foreach(t => {
      if (t.isPointInCircumcircle(newPoint)) {
        badTriangles += t
      }
    })
    // A set of edges of the polygonal hole created by adding the new point
    // represented by the pair of UUIDs for the edge's vertices
    val polygon = mutable.Set[(UUID, UUID)]()
    badTriangles.foreach(t => {
      for (e <- List((t.idA, t.idB), (t.idB, t.idC), (t.idC, t.idA))) {
        var edgeShared = false // Start by assuming the edge is not shared
        // check all other triangles in badTriangles except this one
        badTriangles.diff(Set[Triangle](t)).foreach(other => {
          if (other.isEdgeInThis(e._1, e._2)) edgeShared = true
        })
        if (!edgeShared) {
          polygon.add(e)
        }
      }
    })
    // Remove bad triangles from list of triangles
    badTriangles.foreach(t => {
      val idx = triangles.indexOf(t)
      triangles.remove(idx)
    })
    // Create new triangles connecting each edge in polygon to newPoint
    polygon.foreach(e => {
      val triangle = new Triangle((pointID, newPoint), (e._1, vertices(e._1)), (e._2, vertices(e._2)))
      triangles.addOne(triangle)
    })
  }

  def drawTriangulation(filename: String): Unit = {
    val writer = new ImageWriter(xDimension, yDimension)

    // First draw all the triangles in grey
    writer.setColor("grey")
    writer.setLineWidth(3.0f)
    triangles.foreach(t => {
      writer.drawLine(t.vertA, t.vertB)
      writer.drawLine(t.vertB, t.vertC)
      writer.drawLine(t.vertC, t.vertA)
    })
    // Then draw the input points in black
    writer.setColor("black")
    vertices.foreach(v => {
      writer.drawPoint(v._2, 9)
    })

    writer.writeImage(filename)
  }

  def printTriangulation(): Unit = {

    triangles.foreach(t => println(s"\n$t"))
  }

  private def addSuperTriangle(): Unit = {
    val p1 = new Point(xDimension / 2, 5 * yDimension)
    val p2 = new Point(-5 * xDimension, -5 * yDimension)
    val p3 = new Point(5 * xDimension, -5 * yDimension)
    val supertriangle = new Triangle((UUID(0, 1), p1), (UUID(0, 2), p2), (UUID(0, 3), p3))
    triangles += supertriangle
    vertices += (UUID(0, 1) -> p1, UUID(0, 2) -> p2, UUID(0, 3) -> p3)
  }

}
