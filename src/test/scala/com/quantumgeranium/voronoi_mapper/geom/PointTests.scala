package com.quantumgeranium.voronoi_mapper.geom

import com.quantumgeranium.voronoi_mapper.helpers.BaseTestSpec

class PointTests extends BaseTestSpec {

  val p1 = new Point(1.0, 0.0)
  val p2 = new Point(-1.0, 0.0)
  val p3 = new Point(0.0, 1.0)
  val p4 = new Point(0.0, -1.0)

  "A Point" should "calculate distance along x-axis" in {
    p1.distanceTo(p2) shouldBe 2.0
  }

  it should "calculate the same distance along x-axis in reverse" in {
    p2.distanceTo(p1) shouldBe 2.0
  }

  it should "calculate distance along y-axis" in {
    p3.distanceTo(p4) shouldBe 2.0
  }

  it should "calculate the same distance along y-axis in reverse" in {
    p4.distanceTo(p3) shouldBe 2.0
  }

  it should "calculate distance along the diagonal" in {
    p1.distanceTo(p3) shouldBe math.sqrt(2)
  }

  it should "calculate the same distance along the diagonal in reverse" in {
    p3.distanceTo(p1) shouldBe math.sqrt(2)
  }

}
