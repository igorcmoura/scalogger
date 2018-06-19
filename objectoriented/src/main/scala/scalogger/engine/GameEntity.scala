package scalogger.engine

import javafx.scene.layout.Pane

trait GameEntity {
  def attachToScreen(screen: Pane)
  def detachFromScreen(screen: Pane)

  def processInput(): Unit = {}
  def update(deltaTime: Double): Unit = {}
  def render(): Unit = {}
}
