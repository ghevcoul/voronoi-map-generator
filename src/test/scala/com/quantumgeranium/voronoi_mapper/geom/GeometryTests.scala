package com.quantumgeranium.voronoi_mapper.geom

import com.quantumgeranium.voronoi_mapper.helpers.BaseTestSpec
import org.scalatest.PrivateMethodTester

class GeometryTests extends BaseTestSpec with PrivateMethodTester {

  // Testcases are modelled on the diagrams at this link, with naming matching this page's
  // https://martin-thoma.com/how-to-check-if-two-line-segments-intersect/

  // Testcase T1
  "doLinesIntersect" should "return true when lines are perpendicular intersecting at (0, 0)" in {
    val lineA = new Line(new Point(-5, 0), new Point(5, 0))
    val lineB = new Line(new Point(0, -5), new Point(0, 5))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe true
  }

  // T2
  it should "return true when lines touch at one point" in {
    val lineA = new Line(new Point(0, 0), new Point(10, 10))
    val lineB = new Line(new Point(2, 2), new Point(12, 4))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe true
  }

  // T3
  it should "return true when lines touch at one point at a right angle" in {
    val lineA = new Line(new Point(-2, -4), new Point(-2, 4))
    val lineB = new Line(new Point(-2, 0), new Point(0, 0))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe true
  }

  // T4
  it should "return true when lines touch at one point at a right angle going negative" in {
    val lineA = new Line(new Point(2, -4), new Point(2, 4))
    val lineB = new Line(new Point(2, 2), new Point(-1, 2))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe true
  }

  // T5
  it should "return true when one line is a segment of the other" in {
    val lineA = new Line(new Point(0, 0), new Point(9, 9))
    val lineB = new Line(new Point(2, 2), new Point(6, 6))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe true
  }

  // T6
  it should "return true when lines are identical" in {
    val lineA = new Line(new Point(0, 0), new Point(-9, 9))
    val lineB = new Line(new Point(0, 0), new Point(-9, 9))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe true
  }

  // F1
  it should "return false when lines are parallel, one shorter than the other" in {
    val lineA = new Line(new Point(4, 4), new Point(12, 12))
    val lineB = new Line(new Point(6, 8), new Point(8, 10))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe false
  }

  // F2
  it should "return false when lines are parallel and offset" in {
    val lineA = new Line(new Point(0, 0), new Point(-4, 6))
    val lineB = new Line(new Point(-4, 2), new Point(-8, 8))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe false
  }

  // F3
  it should "return false when lines are parallel and offset with no overlap" in {
    val lineA = new Line(new Point(0, 0), new Point(0, 2))
    val lineB = new Line(new Point(4, 4), new Point(6, 4))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe false
  }

  // F4
  it should "return false when lines are perpendicular, but non-intersecting" in {
    val lineA = new Line(new Point(0, 0), new Point(0, 2))
    val lineB = new Line(new Point(4, 4), new Point(4, 6))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe false
  }

  // F5
  it should "return false when lines are non-overlapping segments of the same line" in {
    val lineA = new Line(new Point(-2, -2), new Point(4, 4))
    val lineB = new Line(new Point(6, 6), new Point(10, 10))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe false
  }

  // F6
  it should "return false when lines are perpendicular and close, but not touching" in {
    val lineA = new Line(new Point(0, 0), new Point(2, 2))
    val lineB = new Line(new Point(4, 0), new Point(1, 4))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe false
  }

  // F7
  it should "return false when lines are zero slope and parallel" in {
    val lineA = new Line(new Point(2, 2), new Point(8, 2))
    val lineB = new Line(new Point(4, 4), new Point(6, 4))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe false
  }

  // F8
  it should "return false when lines are at an angle and close, but not touching" in {
    val lineA = new Line(new Point(4, 2), new Point(4, 4))
    val lineB = new Line(new Point(0, 8), new Point(10, 0))

    val result = Geometry.doLinesIntersect(lineA, lineB)
    result shouldBe false
  }

  private val doBoundingBoxesIntersect = PrivateMethod[Boolean](Symbol("doBoundingBoxesIntersect"))
  private val isPointOnLine = PrivateMethod[Boolean](Symbol("isPointOnLine"))
  private val isPointRightOfLine = PrivateMethod[Boolean](Symbol("isPointRightOfLine"))
  private val doesLineSegmentTouchOrCrossLine = PrivateMethod[Boolean](Symbol("doesLineSegmentTouchOrCrossLine"))

  "doBoundingBoxesIntersect" should "return false when the lines are parallel along an axis" in {
    val lineA = new Line(new Point(0, 0), new Point(0, 5))
    val lineB = new Line(new Point(4, 0), new Point(4, 5))

    val result = Geometry invokePrivate doBoundingBoxesIntersect(lineA, lineB)
    result shouldBe false
  }

  it should "return true when the lines are perpendicular" in {
    val lineA = new Line(new Point(0, 0), new Point(0, 5))
    val lineB = new Line(new Point(-2, 2), new Point(2, 2))

    val result = Geometry invokePrivate doBoundingBoxesIntersect(lineA, lineB)
    result shouldBe true
  }

  it should "return true when the lines are parallel on an angle and close to each other" in {
    val lineA = new Line(new Point(0, 0), new Point(5, 5))
    val lineB = new Line(new Point(-2, -2), new Point(3, 3))

    val result = Geometry invokePrivate doBoundingBoxesIntersect(lineA, lineB)
    result shouldBe true
  }

  it should "return false when lines are non-overlapping segments of same line" in {
    val lineA = new Line(new Point(0, 0), new Point(8, 8))
    val lineB = new Line(new Point(10, 10), new Point(15, 15))

    val result = Geometry invokePrivate doBoundingBoxesIntersect(lineA, lineB)
    result shouldBe false
  }

  "isPointOnLine" should "return true when the point is on an x-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(5, 0))
    val point = new Point(2.5, 0)

    val result = Geometry invokePrivate isPointOnLine(line, point)
    result shouldBe true
  }

  it should "return false when the point is not an x-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(5, 0))
    val point = new Point(2.5, 1)

    val result = Geometry invokePrivate isPointOnLine(line, point)
    result shouldBe false
  }

  it should "return true when the point is on an y-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(0, 5))
    val point = new Point(0, 2.5)

    val result = Geometry invokePrivate isPointOnLine(line, point)
    result shouldBe true
  }

  it should "return false when the point is not on an y-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(0, 5))
    val point = new Point(1, 2.5)

    val result = Geometry invokePrivate isPointOnLine(line, point)
    result shouldBe false
  }

  it should "return true when the point is on a diagonal line" in {
    val line = new Line(new Point(0, 0), new Point(5, 5))
    val point = new Point(2.5, 2.5)

    val result = Geometry invokePrivate isPointOnLine(line, point)
    result shouldBe true
  }

  it should "return false when the point is not a diagonal line" in {
    val line = new Line(new Point(0, 0), new Point(5, 5))
    val point = new Point(2.5, 1)

    val result = Geometry invokePrivate isPointOnLine(line, point)
    result shouldBe false
  }

  it should "return true when point is on same line as line segment" in {
    val line = new Line(new Point(0, 0), new Point(5, 5))
    val point = new Point(7, 7)

    val result = Geometry invokePrivate isPointOnLine(line, point)
    result shouldBe true
  }

  "isPointRightOfLine" should "return true when point is right of x-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(5, 0))
    val point = new Point(2, -1)

    val result = Geometry invokePrivate isPointRightOfLine(line, point)
    result shouldBe true
  }

  it should "return false when point is left of x-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(5, 0))
    val point = new Point(2, 1)

    val result = Geometry invokePrivate isPointRightOfLine(line, point)
    result shouldBe false
  }

  it should "return true when point is right of negative x-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(-5, 0))
    val point = new Point(-2, 1)

    val result = Geometry invokePrivate isPointRightOfLine(line, point)
    result shouldBe true
  }

  it should "return false when point is left of negative x-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(-5, 0))
    val point = new Point(-2, -1)

    val result = Geometry invokePrivate isPointRightOfLine(line, point)
    result shouldBe false
  }

  it should "return true when point is right of y-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(0, 5))
    val point = new Point(1, 2)

    val result = Geometry invokePrivate isPointRightOfLine(line, point)
    result shouldBe true
  }

  it should "return false when point is left of y-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(0, 5))
    val point = new Point(-1, 2)

    val result = Geometry invokePrivate isPointRightOfLine(line, point)
    result shouldBe false
  }

  it should "return true when point is right of negative y-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(0, -5))
    val point = new Point(-1, -2)

    val result = Geometry invokePrivate isPointRightOfLine(line, point)
    result shouldBe true
  }

  it should "return false when point is left of negative y-axis aligned line" in {
    val line = new Line(new Point(0, 0), new Point(0, -5))
    val point = new Point(1, -2)

    val result = Geometry invokePrivate isPointRightOfLine(line, point)
    result shouldBe false
  }

  "doesLineSegmentTouchOrCrossLine" should "return true when lines are perpendicular intersecting at (0, 0)" in {
    // Testcase 1
    val lineA = new Line(new Point(-5, 0), new Point(5, 0))
    val lineB = new Line(new Point(0, -5), new Point(0, 5))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe true
  }

  it should "return true when lines are perpendicular intersecting at (0, 0) and reversed" in {
    val lineA = new Line(new Point(-5, 0), new Point(5, 0))
    val lineB = new Line(new Point(0, -5), new Point(0, 5))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe true
  }

  it should "return true when lines touch at one point" in {
    // Testcase 2
    val lineA = new Line(new Point(0, 0), new Point(10, 10))
    val lineB = new Line(new Point(2, 2), new Point(12, 4))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe true
  }

  it should "return true when lines touch at one point and reversed" in {
    val lineA = new Line(new Point(0, 0), new Point(10, 10))
    val lineB = new Line(new Point(2, 2), new Point(12, 4))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe true
  }

  it should "return true when lines touch at one point at a right angle" in {
    // Testcase 3
    val lineA = new Line(new Point(-2, -4), new Point(-2, 4))
    val lineB = new Line(new Point(-2, 0), new Point(0, 0))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe true
  }

  it should "return true when lines touch at one point at a right angle and reversed" in {
    val lineA = new Line(new Point(-2, -4), new Point(-2, 4))
    val lineB = new Line(new Point(-2, 0), new Point(0, 0))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe true
  }

  it should "return true when lines touch at one point at a right angle going negative" in {
    // Testcase 4
    val lineA = new Line(new Point(2, -4), new Point(2, 4))
    val lineB = new Line(new Point(2, 2), new Point(-1, 2))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe true
  }

  it should "return true when lines touch at one point at a right angle going negative and reversed" in {
    val lineA = new Line(new Point(2, -4), new Point(2, 4))
    val lineB = new Line(new Point(2, 2), new Point(-1, 2))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe true
  }

  it should "return true when one line is a segment of the other" in {
    // Testcase 5
    val lineA = new Line(new Point(0, 0), new Point(9, 9))
    val lineB = new Line(new Point(2, 2), new Point(6, 6))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe true
  }

  it should "return true when when one line is a segment of the other and reversed" in {
    val lineA = new Line(new Point(0, 0), new Point(9, 9))
    val lineB = new Line(new Point(2, 2), new Point(6, 6))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe true
  }

  it should "return true when lines are identical" in {
    // Testcase 6
    val lineA = new Line(new Point(0, 0), new Point(-9, 9))
    val lineB = new Line(new Point(0, 0), new Point(-9, 9))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe true
  }

  it should "return true when when lines are identical and reversed" in {
    val lineA = new Line(new Point(0, 0), new Point(9, 9))
    val lineB = new Line(new Point(0, 0), new Point(-9, 9))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe true
  }

  it should "return false when lines are parallel" in {
    // Testcase 7
    val lineA = new Line(new Point(9, -1), new Point(1, 7))
    val lineB = new Line(new Point(5, 1), new Point(3, 3))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe false
  }

  it should "return false when lines are parallel and reversed" in {
    val lineA = new Line(new Point(9, -1), new Point(1, 7))
    val lineB = new Line(new Point(5, 1), new Point(3, 3))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe false
  }

  it should "return false when lines are parallel and offset" in {
    // Testcase 8
    val lineA = new Line(new Point(0, 0), new Point(-11, 11))
    val lineB = new Line(new Point(-11, 5), new Point(-20, 16))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe false
  }

  it should "return false when lines are parallel and offset and reversed" in {
    val lineA = new Line(new Point(0, 0), new Point(-11, 11))
    val lineB = new Line(new Point(-11, 5), new Point(-20, 16))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe false
  }

  it should "return false when lines are parallel and offset aligned with axis" in {
    // Testcase 9
    val lineA = new Line(new Point(0, 0), new Point(0, 3))
    val lineB = new Line(new Point(4, 5), new Point(4, 8))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe false
  }

  it should "return false when lines are parallel and offset aligned with axis and reversed" in {
    val lineA = new Line(new Point(0, 0), new Point(0, 3))
    val lineB = new Line(new Point(4, 5), new Point(4, 8))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe false
  }

  it should "return false when lines are perpendicular and offset aligned with axis" in {
    // Testcase 9
    val lineA = new Line(new Point(0, 0), new Point(0, 3))
    val lineB = new Line(new Point(2, 8), new Point(4, 8))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe false
  }

  it should "return false when lines are perpendicular and offset aligned with axis and reversed" in {
    val lineA = new Line(new Point(0, 0), new Point(0, 3))
    val lineB = new Line(new Point(2, 8), new Point(4, 8))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe false
  }

  it should "return true when lines are unique segments of same line" in {
    // Testcase 10
    val lineA = new Line(new Point(0, 0), new Point(8, 8))
    val lineB = new Line(new Point(10, 10), new Point(15, 15))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineA, lineB)
    result shouldBe true
  }

  it should "return true when lines unique segments of same line and reversed" in {
    val lineA = new Line(new Point(0, 0), new Point(8, 8))
    val lineB = new Line(new Point(10, 10), new Point(15, 15))

    val result = Geometry invokePrivate doesLineSegmentTouchOrCrossLine(lineB, lineA)
    result shouldBe true
  }

}
