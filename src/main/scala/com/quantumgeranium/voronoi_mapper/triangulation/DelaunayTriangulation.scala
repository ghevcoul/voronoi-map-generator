package com.quantumgeranium.voronoi_mapper.triangulation

import com.quantumgeranium.voronoi_mapper.util.Point
import io.jvm.uuid.UUID

import scala.collection.mutable

class DelaunayTriangulation {

  val vertices: mutable.Map[UUID, Point] = mutable.Map()
  val centers: mutable.Map[UUID, Point] = mutable.Map()

}
