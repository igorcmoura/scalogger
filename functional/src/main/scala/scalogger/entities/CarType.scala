package scalogger.entities

import scalafx.scene.image.Image
import scalogger.engine.Direction.Direction

class CarType(private var sprite: Image,
              private var spriteSize: Double,
              private var speed: Double,
              private var direction: Direction) {
  def getSprite: Image = sprite

  def getSpriteSize: Double = spriteSize

  def getSpeed: Double = speed

  def getDirection: Direction = direction
}
