package scalogger.entities

import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import scalogger.engine._

class Car(initialPosition: Vector2, private var carType: CarType, private var gridSize: Int) extends GameEntity with Spawnable[Car] {

  private var position = initialPosition

  private val imageView = new ImageView(carType.getSprite)
  imageView.setFitWidth(gridSize * carType.getSpriteSize)
  imageView.setFitHeight(gridSize)

  override def spawn(): Car = new Car(this.position, this.carType, this.gridSize)

  def getCollisionBox: Box = new Box(position.x - gridSize / 2, position.y - gridSize / 2.1, gridSize * carType.getSpriteSize, gridSize * 0.9)

  override def attachToScreen(screen: Pane): Unit = {
    screen.getChildren.add(this.imageView)
  }

  override def detachFromScreen(screen: Pane): Unit = {
    screen.getChildren.remove(this.imageView)
  }

  override def update(deltaTime: Double): Unit = {
    this.position += Vector2.unit(carType.getDirection) * carType.getSpeed * deltaTime
    if (this.position.x > 16 * gridSize || this.position.x < -2 * gridSize) {
      GameController.removeGameEntity(this)
    }
  }

  override def render(): Unit = {
    imageView.setX(position.x - gridSize / 2)
    imageView.setY(position.y - gridSize / 2)
  }
}
