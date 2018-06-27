package scalogger.entities

import scalafx.scene.image.ImageView
import scalafx.scene.layout.Pane
import scalogger.engine._
import scalogger.managers.GameMap
import scalogger.managers.Resources.Sprite

class Goal(private val position: Vector2, map: GameMap) extends GameEntity {
  private var _captured = false
  private val imageView = new ImageView(Sprite.FROG_WIN)
  imageView.setX(position.x - map.gridSize / 2)
  imageView.setY(position.y - map.gridSize / 2)
  imageView.setFitWidth(map.gridSize)
  imageView.setFitHeight(map.gridSize)

  private val observable = new Observable[Boolean]

  def addObserver(observer: Observer[Boolean]): Unit = {
    this.observable.addObserver(observer)
  }

  def captured: Boolean = _captured

  def capture(): Unit = {
    this._captured = true
    this.observable.notify(this.captured)
  }

  def reset(): Unit = {
    this._captured = false
    this.observable.notify(this.captured)
  }

  override def attachToScreen(screen: Pane): Unit = {
    screen.getChildren.add(imageView)
  }

  override def detachFromScreen(screen: Pane): Unit = {
    screen.getChildren.remove(imageView)
  }

  def getCollisionBox: Box = new Box(
    position.x - (0.5 * map.gridSize / 2),
    position.y - map.gridSize / 2,
    0.5 * map.gridSize,
    map.gridSize
  )

  override def render(): Unit = {
    imageView.setVisible(this.captured)
  }
}
