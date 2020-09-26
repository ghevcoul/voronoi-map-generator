package com.quantumgeranium.voronoi_mapper.graph

import scala.collection.mutable

import io.jvm.uuid._

class DualGraph {

  val edges: mutable.Map[UUID, Edge] = mutable.Map()
  val vertices: mutable.Map[UUID, VertexNode] = mutable.Map()
  val cells: mutable.Map[UUID, CellNode] = mutable.Map()

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
