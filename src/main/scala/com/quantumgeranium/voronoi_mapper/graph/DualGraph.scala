package com.quantumgeranium.voronoi_mapper.graph

import scala.collection.mutable.ArrayBuffer

class DualGraph {

  //TODO: Maybe make these Maps instead of Arrays?
  val edges: ArrayBuffer[Edge] = new ArrayBuffer[Edge]()
  val vertices: ArrayBuffer[VertexNode] = new ArrayBuffer[VertexNode]()
  val cells: ArrayBuffer[CellNode] = new ArrayBuffer[CellNode]()

}
