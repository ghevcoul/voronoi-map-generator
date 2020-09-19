package com.quantumgeranium.voronoi_mapper

object VoronoiMapper {

  def main(args: Array[String]): Unit = {

    val voronoiDiagram: VoronoiDiagram = new VoronoiDiagram
    // For debugging purposes write out just the initialized center points
    voronoiDiagram.writeDiagram("center_points.png")

    voronoiDiagram.generate()
  }

}
