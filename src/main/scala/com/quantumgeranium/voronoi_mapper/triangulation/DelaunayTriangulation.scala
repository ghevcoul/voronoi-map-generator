package com.quantumgeranium.voronoi_mapper.triangulation

import com.quantumgeranium.voronoi_mapper.ImageWriter
import com.quantumgeranium.voronoi_mapper.graph.{CellNode, DualGraph, Edge, VertexNode}
import com.quantumgeranium.voronoi_mapper.geom.Point
import io.jvm.uuid._

import scala.collection.mutable
import scala.collection.immutable

class DelaunayTriangulation(val xDimension: Int, val yDimension: Int) {

  val vertices: mutable.Map[UUID, Point] = mutable.Map()
  val triangles: mutable.ArrayBuffer[Triangle] = mutable.ArrayBuffer[Triangle]()
  addSuperTriangle()

  def addPoint(newPoint: Point): Unit = {
    val pointID = UUID.random
    vertices += (pointID -> newPoint)
    // Check if the new point is inside the circumcircle of any existing triangle
    val badTriangles = mutable.Set[Triangle]()
    triangles.foreach(t => {
      if (t.isPointInCircumcircle(newPoint)) {
        badTriangles += t
      }
    })
    // A set of edges of the polygonal hole created by adding the new point
    // represented by the pair of UUIDs for the edge's vertices
    val polygon = mutable.Set[(UUID, UUID)]()
    badTriangles.foreach(t => {
      for (e <- List((t.idA, t.idB), (t.idB, t.idC), (t.idC, t.idA))) {
        var edgeShared = false // Start by assuming the edge is not shared
        // check all other triangles in badTriangles except this one
        badTriangles.diff(Set[Triangle](t)).foreach(other => {
          if (other.isEdgeInThis(e._1, e._2)) edgeShared = true
        })
        if (!edgeShared) {
          polygon.add(e)
        }
      }
    })
    // Remove bad triangles from list of triangles
    badTriangles.foreach(t => {
      val idx = triangles.indexOf(t)
      triangles.remove(idx)
    })
    // Create new triangles connecting each edge in polygon to newPoint
    polygon.foreach(e => {
      val triangle = new Triangle((pointID, newPoint), (e._1, vertices(e._1)), (e._2, vertices(e._2)))
      triangles.addOne(triangle)
    })
  }

  def convertToDualGraph(): DualGraph = {
    val dg = new DualGraph

    // Convert the list of triangle vertices in to CellNodes
    for ((id, pos) <- vertices) {
      dg.addCellNode(new CellNode(id, pos))
    }

    // The circumcenter of a triangle will be one of the two VertexNodes associated with each edge of the triangle
    // Loop through all the triangles creating Nodes for the Cells and Vertices
    // At the same time, build an intermediate Map that will hold List(cellID, cellID) -> List(vertexID, vertexID) associations
    // Then once we've determined the full associations, iterate over the Map and construct the Graph edges
    val cellVertexMap: mutable.Map[immutable.SortedSet[UUID], mutable.Set[UUID]] = mutable.Map()
    triangles.foreach(t => {
      val circumcenter = t.circumcenter()
      val circumID = UUID.random

      dg.addVertexNode(new VertexNode(circumID, circumcenter))

      val abSet = immutable.SortedSet[UUID](t.idA, t.idB)
      cellVertexMap.addOne(abSet -> cellVertexMap.getOrElse(abSet, mutable.Set[UUID]()).addOne(circumID))
      val bcSet = immutable.SortedSet[UUID](t.idB, t.idC)
      cellVertexMap.addOne(bcSet -> cellVertexMap.getOrElse(bcSet, mutable.Set[UUID]()).addOne(circumID))
      val caSet = immutable.SortedSet[UUID](t.idC, t.idA)
      cellVertexMap.addOne(caSet -> cellVertexMap.getOrElse(caSet, mutable.Set[UUID]()).addOne(circumID))
    })

    for ((cells, vertexes) <- cellVertexMap) {
      assert(vertexes.size <= 2, "Found cell pair with more than 2 vertices")
      // Skip edges where both cells were in the starting supertriangle
      val superTriangleIDs = List(UUID(0, 1), UUID(0, 2), UUID(0, 3))
      if (!superTriangleIDs.contains(cells.head) && !superTriangleIDs.contains(cells.tail.head)) {
        val cellA = dg.cells(cells.head)
        val cellB = dg.cells(cells.tail.head)
        val vertA = dg.vertices(vertexes.head)
        val vertB = dg.vertices(vertexes.tail.head)
        val edge = new Edge(UUID.random, cellA, cellB, vertA, vertB)
        cellA.addEdge(edge)
        cellB.addEdge(edge)
        vertA.addEdge(edge)
        vertB.addEdge(edge)
        dg.addEdge(edge)
      }
    }

    dg
  }

  def drawTriangulation(filename: String): Unit = {
    val writer = new ImageWriter(xDimension, yDimension)

    // First draw all the triangles in grey
    writer.setColor("grey")
    writer.setLineWidth(3.0f)
    triangles.foreach(t => {
      writer.drawLine(t.vertA, t.vertB)
      writer.drawLine(t.vertB, t.vertC)
      writer.drawLine(t.vertC, t.vertA)
    })
    // Then draw the input points in black
    writer.setColor("black")
    vertices.foreach(v => {
      writer.drawPoint(v._2, 9)
    })

    writer.writeImage(filename)
  }

  def printTriangulation(): Unit = {
    triangles.foreach(t => println(s"\n$t"))
  }

  private def addSuperTriangle(): Unit = {
    val p1 = new Point(xDimension / 2, 5 * yDimension)
    val p2 = new Point(-5 * xDimension, -5 * yDimension)
    val p3 = new Point(5 * xDimension, -5 * yDimension)
    val supertriangle = new Triangle((UUID(0, 1), p1), (UUID(0, 2), p2), (UUID(0, 3), p3))
    triangles += supertriangle
    vertices.addOne(UUID(0, 1) -> p1)
    vertices.addOne(UUID(0, 2) -> p2)
    vertices.addOne(UUID(0, 3) -> p3)
  }

}
