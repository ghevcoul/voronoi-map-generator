package com.quantumgeranium.voronoi_mapper.geom

import com.quantumgeranium.voronoi_mapper.helpers.BaseTestSpec
import org.scalatest.PrivateMethodTester

class GeometryTests extends BaseTestSpec with PrivateMethodTester {

  val lineA = new Line(new Point(0, 0), new Point(0, 5))
  val lineB = new Line(new Point(4, 0), new Point(4, 5))
  val lineC = new Line(new Point(-2, 2), new Point(2, 2))

  private val doBoundingBoxesIntersect = PrivateMethod[Boolean](Symbol("doBoundingBoxesIntersect"))

  "doBoundingBoxesIntersect" should "return false when the lines are parallel" in {
    val result = Geometry invokePrivate doBoundingBoxesIntersect(lineA, lineB)
    result shouldBe false
  }

  it should "return true when the lines are perpendicular" in {
    val result = Geometry invokePrivate doBoundingBoxesIntersect(lineA, lineC)
    result shouldBe true
  }

}
