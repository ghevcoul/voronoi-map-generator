package com.quantumgeranium.voronoi_mapper.graph

import com.quantumgeranium.voronoi_mapper.ImageWriter
import com.quantumgeranium.voronoi_mapper.geom.BoundingBox

import scala.collection.mutable
import io.jvm.uuid._

class DualGraph {

  val edges: mutable.Map[UUID, Edge] = mutable.Map()
  val vertices: mutable.Map[UUID, VertexNode] = mutable.Map()
  val cells: mutable.Map[UUID, CellNode] = mutable.Map()

  // Runs through a list of properties of the graph and its nodes and edges
  // that won't be changing after this point and pre-computes them so it won't
  // have to be done later
  def computeGraphProperties(bBox: BoundingBox): Unit = {
    findCellsOutsideBoundingBox(bBox)

    cells.foreach{ case (id, node) => node.computeProperties() }
  }

  def drawStartingGraph(xDim: Int, yDim: Int): Unit = {
    val writer = new ImageWriter(xDim, yDim)

    // Draw the cells
    cells.foreach { case (_, c) =>
      // If a border cell, draw polygon in blue
      if (c.isBorder) {
        writer.setColor("blue")
        writer.drawPolygon(c.getPolygon)
      }
      writer.setColor("red")
      writer.drawPoint(c.position)
    }

    // Draw all the edges
    writer.setColor("grey")
    writer.setLineWidth(3.0f)
    edges.foreach {
      case (_, e) => writer.drawLine(e.vertexA.position, e.vertexB.position)
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

  // Loops through the VertexNodes, determines if each vertex is outside BBox
  // If it is, label each associated CellNode as "OnBorder"
  private def findCellsOutsideBoundingBox(bBox: BoundingBox): Unit = {
    vertices.foreach{ case (id, node) =>
      if (!node.position.inBoundingBox(bBox)) {
        node.getEdges.foreach(e => e.cellNodes.foreach(cell => cell.setBorder()))
      }
    }
  }

}
