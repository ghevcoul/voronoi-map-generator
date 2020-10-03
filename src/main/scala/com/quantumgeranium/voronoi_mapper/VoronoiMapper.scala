package com.quantumgeranium.voronoi_mapper

object VoronoiMapper {

  def main(args: Array[String]): Unit = {

    val voronoiDiagram: VoronoiDiagram = new VoronoiDiagram

    voronoiDiagram.generate()
  }

}
