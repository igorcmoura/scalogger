package scalogger.entities

import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import scalogger.engine.Direction.RIGHT
import scalogger.engine.{GameEntity, Vector2}

class Car(initialPosition: Vector2, private var carType: CarType, private var gridSize: Int) extends GameEntity {
  private var position = initialPosition


  private val imageView = new ImageView(carType.getSprite)
  imageView.setFitWidth(gridSize * carType.getSpriteSize)
  imageView.setFitHeight(gridSize)

  override def attachToScreen(screen: Pane): Unit = {
    screen.getChildren.add(this.imageView)
  }

  override def detachFromScreen(screen: Pane): Unit = {
    screen.getChildren.remove(this.imageView)
  }

  override def update(deltaTime: Double): Unit = {
    this.position += Vector2.unit(RIGHT) * carType.getSpeed * deltaTime
  }

  override def render(): Unit = {
    imageView.setX(position.x - gridSize / 2)
    imageView.setY(position.y - gridSize / 2)
  }
}
