package com.quantumgeranium.voronoi_mapper.graph

import com.quantumgeranium.voronoi_mapper.util.Point

import scala.collection.mutable.ArrayBuffer

abstract class Node (val position: Point) {

  val edges: ArrayBuffer[Edge] = new ArrayBuffer[Edge]()

  def addEdge(e: Edge): Unit = {
    edges += e
  }

  def removeEdge(e: Edge): Unit = {
    val pos = edges.indexOf(e)
    if (pos == -1) throw new NoSuchElementException(s"Could not find edge $e")
    edges.remove(pos)
  }

}
