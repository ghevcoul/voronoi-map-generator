package com.quantumgeranium.voronoi_mapper.util

class Point(val x: Double, val y: Double) {

  def distanceTo(other: Point): Double = {
    math.sqrt(math.pow(this.x - other.x, 2) + math.pow(this.y - other.y, 2))
  }

}
