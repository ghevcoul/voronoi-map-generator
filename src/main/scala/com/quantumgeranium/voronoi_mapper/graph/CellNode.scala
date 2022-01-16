package com.quantumgeranium.voronoi_mapper.graph

import com.quantumgeranium.voronoi_mapper.geom.Geometry.computeCenterPoint
import com.quantumgeranium.voronoi_mapper.geom.Point
import io.jvm.uuid._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class CellNode(val id: UUID, val position: Point) {

  private var onBorder = false
  private val edges: ArrayBuffer[Edge] = new ArrayBuffer[Edge]()
  private var centerPoint: Point = new Point(0.0, 0.0)
  private var vertices: List[VertexNode] = List()
  private var polygon: List[Point] = List()

  // Work with edges
  def addEdge(e: Edge): Unit = {
    edges += e
  }

  def addEdges(e: ArrayBuffer[Edge]): Unit = {
    edges.addAll(e)
  }

  def removeEdge(e: Edge): Unit = {
    val pos = edges.indexOf(e)
    if (pos == -1) throw new NoSuchElementException(s"Could not find edge $e")
    edges.remove(pos)
  }

  def getEdges: ArrayBuffer[Edge] = edges

  // Border
  def setBorder(border: Boolean = true): Unit = {
    onBorder = border
  }

  def isBorder: Boolean = onBorder

  def getPolygon: List[Point] = polygon

  // Computes node properties for fast access later
  def computeProperties(): Unit = {
    // Get all the vertices associated with this CellNode
    val vertexSet = mutable.Set[VertexNode]()
    edges.foreach(e => vertexSet.addAll(e.vertexNodes))

    // Compute the center point of this node
    centerPoint = computeCenterPoint(vertexSet.map(v => v.position).toList)

    // Sort the vertices in clockwise order
    vertices = vertexSet.toList sortWith(vertexLessThan(_, _))
    polygon = vertices.map(v => v.position)
  }

  // Compares two vertices and returns true if a < b
  // a < b if it is "before" b in clockwise direction around the center point
  // Written to be used as the comparison function when sorting a list of Vertices
  // Math comes from https://stackoverflow.com/questions/6989100/sort-points-in-clockwise-order
  private def vertexLessThan(a: VertexNode, b: VertexNode): Boolean = {
    val aPos = a.position
    val bPos = b.position

    if (aPos.x - centerPoint.x >= 0.0 && bPos.x - centerPoint.x < 0.0) return true
    if (aPos.x - centerPoint.x < 0.0 && bPos.x - centerPoint.x >= 0.0) return false
    if (aPos.x - centerPoint.x == 0.0 && bPos.x - centerPoint.x == 0.0) {
      if (aPos.y - centerPoint.y >= 0.0 || bPos.y - centerPoint.y >= 0.0) {
        return aPos.y > bPos.y
      } else {
        return bPos.y < aPos.y
      }
    }

    val det = (aPos.x - centerPoint.x) * (bPos.y - centerPoint.y) - (bPos.x - centerPoint.x) * (aPos.y - centerPoint.y)
    if (det < 0.0) {
      return true
    } else if (det > 0.0) {
      return false
    }

    // a and b are on the same line from the center
    // check which one is closer to the center
    val d1 = (aPos.x - centerPoint.x) * (aPos.x - centerPoint.x) + (aPos.y - centerPoint.y) * (aPos.y - centerPoint.y)
    val d2 = (bPos.x - centerPoint.x) * (bPos.x - centerPoint.x) + (bPos.y - centerPoint.y) * (bPos.y - centerPoint.y)
    d1 > d2
  }

  // Comparison functions to determine if this node is the same as another node

  // Can these objects even be compared?
  def canEqual(other: Any): Boolean = other.isInstanceOf[CellNode]

  // Are these objects equal?
  override def equals(other: Any): Boolean = {
    other match {
      case other: CellNode => canEqual(other) && id == other.id
      case _ => false
    }
  }

  override def hashCode(): Int = id.hashCode()

}
