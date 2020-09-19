package com.quantumgeranium.voronoi_mapper.util

import scala.collection.mutable.ArrayBuffer

class Cell(val p: Point, val id: Int) {

  val center: Point = p
  val edges: ArrayBuffer[Line] = new ArrayBuffer[Line]()

  def addEdge(edge: Line): Unit = edges += edge

  def deleteEdge(edge: Line): Unit = {
    val pos = edges.indexOf(edge)
    if (pos == -1) throw new NoSuchElementException(s"Could not find edge $edge")
    edges.remove(pos)
  }

  override def toString: String = {
    var output = s"Cell $id\n  center = $center\n" + s"  edges = [\n"
    edges.foreach(e => output = output.concat(s"    $e\n"))
    output.concat("  ]")
  }

}
