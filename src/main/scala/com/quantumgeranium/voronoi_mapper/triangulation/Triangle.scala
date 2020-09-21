package com.quantumgeranium.voronoi_mapper.triangulation

import com.quantumgeranium.voronoi_mapper.util.Point
import io.jvm.uuid.UUID
import org.la4j.matrix.DenseMatrix

class Triangle(val vertex1: (UUID, Point), val vertex2: (UUID, Point), val vertex3: (UUID, Point)) {

  // For later triangle operations, the points need to be sorted in counterclockwise order
  private val sortedVertices = sortVertices()
  private val vertA = sortedVertices._1
  private val vertB = sortedVertices._2
  private val vertC = sortedVertices._3

  def isPointInCircumcircle(testPoint: Point): Boolean = {
    val row1 = Array[Double](
      vertA._2.x - testPoint.x,
      vertA._2.y - testPoint.y,
      math.pow(vertA._2.x - testPoint.x, 2) + math.pow(vertA._2.y - testPoint.y, 2)
    )
    val row2 = Array[Double](
      vertB._2.x - testPoint.x,
      vertB._2.y - testPoint.y,
      math.pow(vertB._2.x - testPoint.x, 2) + math.pow(vertB._2.y - testPoint.y, 2)
    )
    val row3 = Array[Double](
      vertC._2.x - testPoint.x,
      vertC._2.y - testPoint.y,
      math.pow(vertC._2.x - testPoint.x, 2) + math.pow(vertC._2.y - testPoint.y, 2)
    )
    val matrix = DenseMatrix.from2DArray(Array[Array[Double]](row1, row2, row3))
    matrix.determinant() > 0
  }

  def isEdgeInThis(head: (UUID, Point), tail: (UUID, Point)): Boolean = {
    val headShared = head._1 == vertA._1 || head._1 == vertB._1 || head._1 == vertC._1
    val tailShared = tail._1 == vertA._1 || tail._1 == vertB._1 || tail._1 == vertC._1

    headShared && tailShared
  }

  def circumcenter(): Point = {
    val d = 2.0 * ((vertA._2.x * (vertB._2.y - vertC._2.y)) +
                   (vertB._2.x * (vertC._2.y - vertA._2.y)) +
                   (vertC._2.x * (vertA._2.y - vertB._2.y)))

    val x = (((math.pow(vertA._2.x, 2) + math.pow(vertA._2.y, 2)) * (vertB._2.y - vertC._2.y)) +
             ((math.pow(vertB._2.x, 2) + math.pow(vertB._2.y, 2)) * (vertC._2.y - vertA._2.y)) +
             ((math.pow(vertC._2.x, 2) + math.pow(vertC._2.y, 2)) * (vertA._2.y - vertB._2.y))) / d

    val y = (((math.pow(vertA._2.x, 2) + math.pow(vertA._2.y, 2)) * (vertC._2.x - vertB._2.x)) +
             ((math.pow(vertB._2.x, 2) + math.pow(vertB._2.y, 2)) * (vertA._2.x - vertC._2.x)) +
             ((math.pow(vertC._2.x, 2) + math.pow(vertC._2.y, 2)) * (vertB._2.x - vertA._2.x))) / d

    new Point(x, y)
  }

  // Sort the input vertices in counterclockwise order
  // Output is in the order top, lower-left, lower-right
  private def sortVertices(): ((UUID, Point), (UUID, Point), (UUID, Point)) = {
    var upper, lr, ll: (UUID, Point) = null
    if (vertex1._2.x > vertex2._2.x && vertex1._2.x > vertex3._2.x) {
      upper = vertex1
      if (vertex2._2.y < vertex3._2.y) {
        ll = vertex2
        lr = vertex3
      } else {
        ll = vertex3
        lr = vertex2
      }
    } else if (vertex2._2.x > vertex1._2.x && vertex2._2.x > vertex3._2.x) {
      upper = vertex2
      if (vertex1._2.y < vertex3._2.y) {
        ll = vertex1
        lr = vertex3
      } else {
        ll = vertex3
        lr = vertex1
      }
    } else {
      upper = vertex3
      if (vertex1._2.y < vertex2._2.y) {
        ll = vertex1
        lr = vertex2
      } else {
        ll = vertex2
        lr = vertex1
      }
    }
    (upper, ll, lr)
  }

}
