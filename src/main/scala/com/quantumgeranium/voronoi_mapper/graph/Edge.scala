package com.quantumgeranium.voronoi_mapper.graph

import io.jvm.uuid._

class Edge(val id: UUID,
           val cellA: CellNode,
           val cellB: Option[CellNode],
           val vertexA: VertexNode,
           val vertexB: VertexNode) {

  val vertexNodes = List(vertexA, vertexB)
  val cellNodes = createCellNodeList

  private def createCellNodeList: List[CellNode] = {
    cellB match {
      case Some(nodeB) => List(cellA, nodeB)
      case None => List(cellA)
    }
  }

}
