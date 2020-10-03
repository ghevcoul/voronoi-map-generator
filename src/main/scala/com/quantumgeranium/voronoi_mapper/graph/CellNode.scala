package com.quantumgeranium.voronoi_mapper.graph

import com.quantumgeranium.voronoi_mapper.util.Point

import io.jvm.uuid._

class CellNode(id: UUID = UUID(0, 1), position: Point = new Point(-1.0, -1.0)) extends Node(id, position) {

}
