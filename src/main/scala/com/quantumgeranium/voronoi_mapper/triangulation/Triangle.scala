package com.quantumgeranium.voronoi_mapper.triangulation

import com.quantumgeranium.voronoi_mapper.util.Point
import io.jvm.uuid.UUID
import org.la4j.matrix.DenseMatrix

class Triangle(val vertex1: (UUID, Point), val vertex2: (UUID, Point), val vertex3: (UUID, Point)) {

  // For later triangle operations, the points need to be sorted in counterclockwise order
  private val sortedVertices = sortVertices()
  private val (idA, vertA) = sortedVertices._1
  private val (idB, vertB) = sortedVertices._2
  private val (idC, vertC) = sortedVertices._3

  def isPointInCircumcircle(testPoint: Point): Boolean = {
    val row1 = Array[Double](
      vertA.x - testPoint.x,
      vertA.y - testPoint.y,
      math.pow(vertA.x - testPoint.x, 2) + math.pow(vertA.y - testPoint.y, 2)
    )
    val row2 = Array[Double](
      vertB.x - testPoint.x,
      vertB.y - testPoint.y,
      math.pow(vertB.x - testPoint.x, 2) + math.pow(vertB.y - testPoint.y, 2)
    )
    val row3 = Array[Double](
      vertC.x - testPoint.x,
      vertC.y - testPoint.y,
      math.pow(vertC.x - testPoint.x, 2) + math.pow(vertC.y - testPoint.y, 2)
    )
    val matrix = DenseMatrix.from2DArray(Array[Array[Double]](row1, row2, row3))
    val determinant = matrix.determinant()
    determinant > 0
  }

  def isEdgeInThis(head: (UUID, Point), tail: (UUID, Point)): Boolean = {
    val headShared = head._1 == idA || head._1 == idB || head._1 == idC
    val tailShared = tail._1 == idA || tail._1 == idB || tail._1 == idC

    headShared && tailShared
  }

  def circumcenter(): Point = {
    val d = 2.0 * ((vertA.x * (vertB.y - vertC.y)) +
                   (vertB.x * (vertC.y - vertA.y)) +
                   (vertC.x * (vertA.y - vertB.y)))

    val x = (((math.pow(vertA.x, 2) + math.pow(vertA.y, 2)) * (vertB.y - vertC.y)) +
             ((math.pow(vertB.x, 2) + math.pow(vertB.y, 2)) * (vertC.y - vertA.y)) +
             ((math.pow(vertC.x, 2) + math.pow(vertC.y, 2)) * (vertA.y - vertB.y))) / d

    val y = (((math.pow(vertA.x, 2) + math.pow(vertA.y, 2)) * (vertC.x - vertB.x)) +
             ((math.pow(vertB.x, 2) + math.pow(vertB.y, 2)) * (vertA.x - vertC.x)) +
             ((math.pow(vertC.x, 2) + math.pow(vertC.y, 2)) * (vertB.x - vertA.x))) / d

    new Point(x, y)
  }

  // Sort the input vertices in counterclockwise order
  // Use the method described at https://en.wikipedia.org/wiki/Curve_orientation#Orientation_of_a_simple_polygon
  private def sortVertices(): ((UUID, Point), (UUID, Point), (UUID, Point)) = {
    val row1 = Array[Double](1.0, vertex1._2.x, vertex1._2.y)
    val row2 = Array[Double](1.0, vertex2._2.x, vertex2._2.y)
    val row3 = Array[Double](1.0, vertex3._2.x, vertex3._2.y)
    val matrix = DenseMatrix.from2DArray(Array[Array[Double]](row1, row2, row3))
    // If the determinant is positive the points are already in counterclockwise order
    if (matrix.determinant() > 0) {
      (vertex1, vertex2, vertex3)
    } else {
      // flip vertex1 and vertex3 to put them in order
      (vertex3, vertex2, vertex1)
    }
  }

  override def toString: String = s"$vertA  $idA\n$vertB  $idB\n$vertC  $idC"

}
