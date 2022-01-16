package com.quantumgeranium.voronoi_mapper.geom

import com.quantumgeranium.voronoi_mapper.helpers.BaseTestSpec
import org.scalatest.PrivateMethodTester

class GeometryTests extends BaseTestSpec with PrivateMethodTester {

  private val doBoundingBoxesIntersect = PrivateMethod[Boolean](Symbol("doBoundingBoxesIntersect"))
  private val isPointOnLine = PrivateMethod[Boolean](Symbol("isPointOnLine"))
  private val isPointRightOfLine = PrivateMethod[Boolean](Symbol("isPointRightOfLine"))

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

}
