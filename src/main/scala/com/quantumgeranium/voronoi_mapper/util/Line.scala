package com.quantumgeranium.voronoi_mapper.util

class Line(val head: Point, val tail: Point) {

  def length(): Double = head.distanceTo(tail)

  // Returns a new line that is the perpendicular bisector of this line
  // Does so using the equation from https://en.wikipedia.org/wiki/Bisection#Line_segment_bisector
  def perpendicularBisector(): Line = {
    val slope = (tail.x - head.x) / (tail.y - head.y)
    val middle = midPoint()
    // Determine points on the line at midX +/- 1000
    val x1 = middle.x - 1000.0
    val y1 = (slope * (x1 - middle.x)) + middle.y
    val x2 = middle.x + 1000.0
    val y2 = (slope * (x2 - middle.x)) + middle.y
    new Line(new Point(x1, y1), new Point(x2, y2))
  }

  def midPoint(): Point = {
    new Point((head.x + tail.x) / 2.0, (head.y + tail.y) / 2.0)
  }

  override def toString: String = s"[ $head, $tail ]"

}
