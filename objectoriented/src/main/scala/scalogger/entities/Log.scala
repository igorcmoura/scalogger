package scalogger.entities

import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import scalogger.engine.Direction.RIGHT
import scalogger.engine.Resources.Sprite
import scalogger.engine._

class Log(initialPosition: Vector2,
          private var size: Int,
          private var speed: Double,
          private var map: GameMap) extends Rideable with Spawnable[Log] {
  private var position = initialPosition

  private val imageViews: List[ImageView] = List.fill(size)(new ImageView(Sprite.LOG))
  for (imageView <- imageViews) {
    imageView.setFitWidth(map.gridSize * 11 / 4)
    imageView.setFitHeight(map.gridSize)
  }

  override def spawn(): Log = new Log(this.position, this.size, this.speed, this.map)

  override def attachToScreen(screen: Pane): Unit = {
    for (imageView <- imageViews) {
      screen.getChildren.add(imageView)
    }
  }

  override def detachFromScreen(screen: Pane): Unit = {
    for (imageView <- imageViews) {
      screen.getChildren.remove(imageView)
    }
  }

  override def update(deltaTime: Double): Unit = {
    val movement = Vector2.unit(RIGHT) * speed * deltaTime
    this.position += movement
    if (this.carrying != null) {
      this.carrying.move(movement)
    }
    if (this.position.x > 16 * map.gridSize) {
      GameController.removeGameEntity(this)
    }
  }

  override def render(): Unit = {
    for ((imageView, index) <- imageViews.zipWithIndex) {
      imageView.setX(position.x + (map.gridSize * index) - (map.gridSize / 2))
      imageView.setY(position.y - map.gridSize / 2)
    }
  }

  override def getCollisionBox: Box = new Box(
    position.x - map.gridSize / 2,
    position.y - map.gridSize / 2.1,
    (map.gridSize * 11 / 4) + (size - 1) * map.gridSize,
    map.gridSize * 0.9
  )
}
