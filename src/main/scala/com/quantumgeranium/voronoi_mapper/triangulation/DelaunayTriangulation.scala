package com.quantumgeranium.voronoi_mapper.triangulation

import com.quantumgeranium.voronoi_mapper.util.Point
import io.jvm.uuid._

import scala.collection.mutable

class DelaunayTriangulation(val xDimension: Int, val yDimension: Int) {

  val vertices: mutable.Map[UUID, Point] = mutable.Map()
  val triangles: mutable.ArrayBuffer[Triangle] = mutable.ArrayBuffer[Triangle]()
  addSuperTriangle()

  val centers: mutable.Map[UUID, Point] = mutable.Map()

  def addPoint(newPoint: Point): Unit = {
    val pointID = UUID.random
    vertices += (pointID -> newPoint)
    // Check if the new point is inside the circumcirle of any existing triangle
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
      for (e <- List((t.vertex1, t.vertex2), (t.vertex2, t.vertex3), (t.vertex3, t.vertex1))) {
        var edgeShared = false // Start by assuming the edge is not shared
        // check all other triangles in badTriangles except this one
        badTriangles.diff(Set[Triangle](t)).foreach(other => {
          if (other.isEdgeInThis(e._1, e._2)) edgeShared = true
        })
        if (!edgeShared) {
          polygon.add((e._1._1, e._2._1))
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
      triangles += new Triangle((pointID, newPoint), (e._1, vertices(e._1)), (e._2, vertices(e._2)))
    })
  }

  private def addSuperTriangle(): Unit = {
    val p1 = new Point(xDimension / 2, -10 * yDimension)
    val p2 = new Point(-10 * xDimension, 10 * yDimension)
    val p3 = new Point(10 * xDimension, 10 * yDimension)
    val supertriangle = new Triangle((UUID(0, 1), p1), (UUID(0, 2), p2), (UUID(0, 3), p3))
    triangles += supertriangle
    vertices += (UUID(0, 1) -> p1, UUID(0, 2) -> p2, UUID(0, 3) -> p3)
  }

}
