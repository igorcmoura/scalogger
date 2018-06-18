package scalogger.engine

import javafx.scene.layout.Pane

trait GameEntity {
  def attachToScreen(screen: Pane)
  def detachFromScreen(screen: Pane)

  def readInputs()
  def computePhysics(deltaTime: Double)
  def render()
}
