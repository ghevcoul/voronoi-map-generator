package com.quantumgeranium.voronoi_mapper.geom

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class PointTests extends AnyFlatSpec with should.Matchers {

  val p1 = new Point(1.0, 0.0)
  val p2 = new Point(-1.0, 0.0)
  val p3 = new Point(0.0, 1.0)
  val p4 = new Point(0.0, -1.0)

  "A Point" should "calculate distance along x-axis" in {
    assert(p1.distanceTo(p2) == 2.0)
    assert(p2.distanceTo(p1) == 2.0)
  }

  "A Point" should "calculate distance along y-axis" in {
    assert(p3.distanceTo(p4) == 2.0)
    assert(p4.distanceTo(p3) == 2.0)
  }

  "A Point" should "calculate distance along the diagonal" in {
    assert(p1.distanceTo(p3) == math.sqrt(2))
    assert(p3.distanceTo(p1) == math.sqrt(2))
  }

}
