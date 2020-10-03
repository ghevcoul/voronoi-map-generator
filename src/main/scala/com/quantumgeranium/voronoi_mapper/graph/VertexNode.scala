package com.quantumgeranium.voronoi_mapper.graph

import com.quantumgeranium.voronoi_mapper.util.Point
import io.jvm.uuid._

import scala.collection.mutable.ArrayBuffer

class VertexNode(val id: UUID, val position: Point) {

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

  def getEdges(): ArrayBuffer[Edge] = {
    edges
  }

}
