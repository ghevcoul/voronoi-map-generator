package com.quantumgeranium.voronoi_mapper.graph

import com.quantumgeranium.voronoi_mapper.geom.Point
import io.jvm.uuid._

import scala.collection.mutable.ArrayBuffer

class CellNode(val id: UUID, val position: Point) {

  private var onBorder = false
  private var outside = false
  private val edges: ArrayBuffer[Edge] = new ArrayBuffer[Edge]()

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

  def getEdges(): ArrayBuffer[Edge] = {
    edges
  }

  // Border
  def setBorder(border: Boolean = true): Unit = {
    onBorder = border
  }

  def isBorder(): Boolean = {
    onBorder
  }

  // Outside
  def setOutside(outside: Boolean = true): Unit = {
    this.outside = outside
  }

  def isOutside(): Boolean = {
    outside
  }

  // Loops through the associated vertices and checks if any of them are outside the bounding box
  def insideBoundingBox(xMin: Int, xMax: Int, yMin: Int, yMax: Int): Boolean = {
    var inside = true
    edges.foreach(e => {
      if (!e.vertexA.position.inBoundingBox(xMin, xMax, yMin, yMax)) inside = false
      if (!e.vertexB.position.inBoundingBox(xMin, xMax, yMin, yMax)) inside = false
    })
    inside
  }

}
