package com.quantumgeranium.voronoi_mapper

import com.quantumgeranium.voronoi_mapper.util.Point
import java.awt.{BasicStroke, Color, Graphics2D}
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File

import javax.imageio.ImageIO


class ImageWriter(var xDimension: Int, var yDimension: Int) {

  val image: BufferedImage = new BufferedImage(xDimension, yDimension, TYPE_INT_RGB)
  val graphics: Graphics2D = image.createGraphics()
  graphics.setBackground(Color.WHITE)
  graphics.clearRect(0, 0, xDimension, yDimension)

  // fillOval() sets the top-left corner of the bounding box as p
  // translate the input point so the center of the oval is at point
  def drawPoint(p: Point, size: Int = 5): Unit = {
    val translation = math.ceil(size / 2.0).toInt
    val x = p.x.toInt - translation
    val y = p.y.toInt - translation
    graphics.fillOval(x, y, size, size)
  }

  def drawLine(v1: Point, v2: Point): Unit = {
    graphics.drawLine(v1.x.toInt, v1.y.toInt, v2.x.toInt, v2.y.toInt)
  }

  def writeImage(filename: String): Unit = {
    val file: File = new File(filename)
    ImageIO.write(image, "PNG", file)
  }

  def setColor(color: String): Unit = {
    color.toLowerCase match {
      case "black" => graphics.setColor(Color.BLACK)
      case "blue" => graphics.setColor(Color.BLUE)
      case "green" => graphics.setColor(Color.GREEN)
      case "gray" => graphics.setColor(Color.GRAY)
      case "grey" => graphics.setColor(Color.GRAY)
      case _ => throw new NotImplementedError(s"Unknown color $color")
    }
  }

  def setLineWidth(width: Float): Unit = {
    val stroke = new BasicStroke(width)
    graphics.setStroke(stroke)
  }

}
