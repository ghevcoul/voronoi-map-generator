package com.quantumgeranium.voronoi_mapper.graph

import com.quantumgeranium.voronoi_mapper.ImageWriter

import scala.collection.mutable
import io.jvm.uuid._

class DualGraph {

  val edges: mutable.Map[UUID, Edge] = mutable.Map()
  val vertices: mutable.Map[UUID, VertexNode] = mutable.Map()
  val cells: mutable.Map[UUID, CellNode] = mutable.Map()

  def drawGraph(xDim: Int, yDim: Int): Unit = {
    val writer = new ImageWriter(xDim, yDim)

    // First draw all the edges
    writer.setColor("grey")
    writer.setLineWidth(3.0f)
    edges.foreach {
      case (_, e) => writer.drawLine(e.vertexA.position, e.vertexB.position)
    }

    // Then draw the cell points
    writer.setColor("red")
    cells.foreach {
      case (_, c) => writer.drawPoint(c.position)
    }

    writer.writeImage("voronoi_diagram.png")
  }

  def addCellNode(cellNode: CellNode): Unit = {
    cells += (cellNode.id -> cellNode)
  }

  def isCellNodeInGraph(cellNode: CellNode): Boolean = {
    cells.contains(cellNode.id)
  }

  def addVertexNode(vertexNode: VertexNode): Unit = {
    vertices += (vertexNode.id -> vertexNode)
  }

  def isVertexNodeInGraph(vertexNode: VertexNode): Boolean = {
    vertices.contains(vertexNode.id)
  }

  def addEdge(edge: Edge): Unit = {
    edges += (edge.id -> edge)
  }

  def isEdgeInGraph(edge: Edge): Boolean = {
    edges.contains(edge.id)
  }

}
