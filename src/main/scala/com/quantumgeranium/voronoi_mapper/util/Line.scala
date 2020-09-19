package com.quantumgeranium.voronoi_mapper.util

class Line(val head: Point, val tail: Point) {

  def length(): Double = {
    head.distanceTo(tail)
  }

  override def toString: String = s"[ $head, $tail ]"

}
