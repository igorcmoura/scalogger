package scalogger.entities

import javafx.scene.image.Image

class CarType (private var sprite: Image, private var spriteSize: Double, private var speed: Double) {
  def getSprite: Image = sprite
  def getSpriteSize: Double = spriteSize
  def getSpeed: Double = speed
}
