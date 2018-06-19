package scalogger.engine

import javafx.scene.layout.Pane

trait GameEntity {
  def attachToScreen(screen: Pane)
  def detachFromScreen(screen: Pane)

  def readInputs(): Unit = {}
  def computePhysics(deltaTime: Double): Unit = {}
  def render(): Unit = {}
}
