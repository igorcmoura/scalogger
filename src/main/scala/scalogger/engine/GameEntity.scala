package scalogger.engine

import javafx.scene.image.ImageView

trait GameEntity {
  def getImageView: ImageView

  def readInputs()
  def computePhysics(deltaTime: Double)
  def render()
}
