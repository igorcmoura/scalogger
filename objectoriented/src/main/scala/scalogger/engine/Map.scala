package scalogger.engine

import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import scalogger.engine.Resources.Sprite

class Map(private val _gridSize: Int, private val _mapWidth: Int, private val _playableArea: Box, private val _waterArea: Box) {
  def gridSize: Int = _gridSize
  def mapWidth: Int = _mapWidth
  def playableArea: Box = _playableArea
  def waterArea: Box = _waterArea

  def render(screen: Pane): Unit = {
    renderMap(screen)
  }

  private def renderMap(screen: Pane): Unit = {
    val water = waterArea.toRectangle
    water.setFill(Color.DARKBLUE)
    screen.getChildren.add(water)

    this.renderSideWalk(screen, 8)
    this.renderSideWalk(screen, 14)

    val finalView = new ImageView(Sprite.FINAL)
    finalView.setFitWidth(gridSize * mapWidth)
    finalView.setFitHeight(gridSize * 2)
    finalView.setX(0)
    finalView.setY(1 * gridSize)
    screen.getChildren.add(finalView)
  }

  private def renderSideWalk(screen: Pane, lane: Double): Unit = {
    for (i <- 0 until mapWidth) {
      val sideWalkView = new ImageView(Sprite.SIDEWALK)
      sideWalkView.setFitWidth(gridSize)
      sideWalkView.setFitHeight(gridSize)
      sideWalkView.setX(i * gridSize)
      sideWalkView.setY(lane * gridSize)
      screen.getChildren.add(sideWalkView)
    }
  }
}
