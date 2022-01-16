package com.quantumgeranium.voronoi_mapper.geom

class Line(val head: Point, val tail: Point) {

  // Pre-compute some properties of the line
  val slope: Double = (tail.y - head.y) / (tail.x - head.x)
  val length: Double = head.distanceTo(tail)
  val midPoint: Point = new Point((head.x + tail.x) / 2.0, (head.y + tail.y) / 2.0)

  // Returns a new line that is the perpendicular bisector of this line
  // Does so using the equation from https://en.wikipedia.org/wiki/Bisection#Line_segment_bisector
  def perpendicularBisector(): Line = {
    // Determine points on the line at midX +/- 1000
    val x1 = midPoint.x - 1000.0
    val y1 = (slope * (x1 - midPoint.x)) + midPoint.y
    val x2 = midPoint.x + 1000.0
    val y2 = (slope * (x2 - midPoint.x)) + midPoint.y
    new Line(new Point(x1, y1), new Point(x2, y2))
  }

  override def toString: String = s"[$head, $tail]"

}
