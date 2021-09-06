package com.quantumgeranium.voronoi_mapper.graph

import com.quantumgeranium.voronoi_mapper.geom.Point
import io.jvm.uuid._

import scala.collection.mutable.ArrayBuffer

class VertexNode(val id: UUID, var position: Point) {

  private val edges: ArrayBuffer[Edge] = new ArrayBuffer[Edge]()

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

  def move(newPos: Point): Unit = {
    position = newPos
  }

  // Comparison functions to determine if this node is the same as another node

  // Can these objects even be compared?
  def canEqual(other: Any): Boolean = other.isInstanceOf[VertexNode]

  // Are these objects equal?
  override def equals(other: Any): Boolean = {
    other match {
      case other: VertexNode => canEqual(other) && id == other.id
      case _ => false
    }
  }

  override def hashCode(): Int = id.hashCode()

}
