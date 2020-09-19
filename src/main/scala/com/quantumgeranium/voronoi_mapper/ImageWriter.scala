package com.quantumgeranium.voronoi_mapper

import java.awt.{Color, Graphics2D}
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File

import javax.imageio.ImageIO


class ImageWriter(var xDimension: Int, var yDimension: Int) {

  val image: BufferedImage = new BufferedImage(xDimension, yDimension, TYPE_INT_RGB)
  val graphics: Graphics2D = image.createGraphics()

  def drawPoint(x: Int, y: Int, color: String = "black"): Unit = {
    setColor(color)
    graphics.fillOval(x, y, 1, 1)
  }

  def writeImage(filename: String): Unit = {
    val file: File = new File(filename)
    ImageIO.write(image, "PNG", file)
  }

  private def setColor(color: String): Unit = {
    color.toLowerCase match {
      case "black" => graphics.setColor(Color.BLACK)
      case "blue" => graphics.setColor(Color.BLUE)
      case "green" => graphics.setColor(Color.GREEN)
      case "gray" => graphics.setColor(Color.GRAY)
      case "grey" => graphics.setColor(Color.GRAY)
      case _ => throw new NotImplementedError(s"Unknown color $color")
    }
  }

}
