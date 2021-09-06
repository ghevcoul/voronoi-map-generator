package com.quantumgeranium.voronoi_mapper.geom

class Point(val x: Double, val y: Double) {

  def distanceTo(other: Point): Double = {
    math.sqrt(math.pow(this.x - other.x, 2) + math.pow(this.y - other.y, 2))
  }

  def inBoundingBox(bBox: BoundingBox): Boolean = {
    bBox.xMin <= x && x <= bBox.xMax && bBox.yMin <= y && y <= bBox.yMax
  }

  override def toString: String = f"($x%5.2f, $y%5.2f)"

}
