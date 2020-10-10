package com.quantumgeranium.voronoi_mapper.geom

object Geometry {

  // Cross product of two points
  def crossProduct(a: Point, b: Point): Double = {
    (a.x * b.y) - (a.y * b.x)
  }

  // All the below functionality to determine if the two lines cross and their intersection point from
  // https://martin-thoma.com/how-to-check-if-two-line-segments-intersect/#Where_do_two_line_segments_intersect

  // If the bounding boxes of the two lines intersect and both segments intersect the line defined by the other
  // then the two line segments intersect each other
  def doLinesIntersect(a: Line, b: Line): Boolean = {
    doBoundingBoxesIntersect(a, b) && doesLineSegmentTouchOrCrossLine(a, b) && doesLineSegmentTouchOrCrossLine(b, a)
  }

  // This function assumes that we already know a and b intersect
  def getIntersectionPoint(a: Line, b: Line): Point = {
    val slopeA = slopeOfLine(a)
    val interceptA = a.head.y - (slopeA * a.head.x)
    val slopeB = slopeOfLine(b)
    val interceptB = b.head.y - (slopeB * b.head.x)

    if (slopeA == slopeB) throw new Exception("Cannot compute intersection of parallel lines")

    val x = (interceptB - interceptA) / (slopeA - slopeB)
    val y = (x * slopeA) + interceptA
    new Point(x, y)
  }

  private def slopeOfLine(a: Line): Double = {
    (a.head.y - a.tail.y) / (a.head.x - a.tail.x)
  }

  private def doBoundingBoxesIntersect(a: Line, b: Line): Boolean = {
    // Bottom Left and Top Right corners of A and B
    val aBL = new Point(math.min(a.head.x, a.tail.x), math.min(a.head.y, a.tail.y))
    val aTR = new Point(math.max(a.head.x, a.tail.x), math.max(a.head.y, a.tail.y))
    val bBL = new Point(math.min(b.head.x, b.tail.x), math.min(b.head.y, b.tail.y))
    val bTR = new Point(math.max(b.head.x, b.tail.x), math.max(b.head.y, b.tail.y))

    aBL.x <= bTR.x &&
      aTR.x >= bBL.x &&
      aBL.y <= bTR.y &&
      aTR.y >= bBL.y
  }

  // Check if line segment a touches or crosses the line defined by b
  private def doesLineSegmentTouchOrCrossLine(a: Line, b: Line): Boolean = {
    // other crosses this line if the two ends of other are on opposite sides of this
    // e.g. exactly one of the ends is on the right
    // Also check if the ends of other lie on this line
    isPointOnLine(a, b.head) || isPointOnLine(a, b.tail) || (isPointRightOfLine(a, b.head) ^ isPointRightOfLine(a, b.tail))
  }

  private def isPointOnLine(line: Line, p: Point): Boolean = {
    val tolerance = 0.000001
    // Translate this line and the point so that head is at (0, 0)
    val tempL = new Line(new Point(0.0, 0.0), new Point(line.tail.x - line.head.x, line.tail.y - line.head.y))
    val tempP = new Point(p.x - line.head.x, p.y - line.head.y)

    // Check the cross product of tempL.tail and tempP
    // If abs(cross) < tolerance, the point is on this line
    math.abs(crossProduct(tempL.tail, tempP)) < tolerance
  }

  private def isPointRightOfLine(line: Line, p: Point): Boolean = {
    // Translate this line and the point so that head is at (0, 0)
    val tempL = new Line(new Point(0.0, 0.0), new Point(line.tail.x - line.head.x, line.tail.y - line.head.y))
    val tempP = new Point(p.x - line.head.x, p.y - line.head.y)

    // Check the cross product of tempL.tail and tempP
    // If negative, point is right of the line
    crossProduct(tempL.tail, tempP) < 0.0
  }

}
