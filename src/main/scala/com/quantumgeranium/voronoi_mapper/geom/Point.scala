package com.quantumgeranium.voronoi_mapper.geom

class Point(val x: Double, val y: Double) {

  def distanceTo(other: Point): Double = {
    math.sqrt(math.pow(this.x - other.x, 2) + math.pow(this.y - other.y, 2))
  }

  def inBoundingBox(xMin: Int, xMax: Int, yMin: Int, yMax: Int): Boolean = {
    xMin <= x && x <= xMax && yMin <= y && y <= yMax
  }

  override def toString: String = f"($x%5.2f, $y%5.2f)"

}
