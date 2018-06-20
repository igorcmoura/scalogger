package scalogger.entities

import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import scalogger.engine.Direction.LEFT
import scalogger.engine.Resources.Sprite
import scalogger.engine._

class Turtles(initialPosition: Vector2,
              private var size: Int,
              private var speed: Double,
              private var animationTime: Double,
              private var map: GameMap) extends Rideable with Spawnable[Turtles] {
  private var position = initialPosition

  private val imageViews: List[ImageView] = List.fill(size)(new ImageView(Sprite.TURTLE_1))
  for (imageView <- imageViews) {
    imageView.setFitWidth(map.gridSize)
    imageView.setFitHeight(map.gridSize)
  }
  private val images = Seq(Sprite.TURTLE_1, Sprite.TURTLE_2, Sprite.TURTLE_3)
  private var currentImageIdx = 0
  private var nextImageTime = animationTime / images.size

  override def spawn(): Turtles = new Turtles(this.position, this.size, this.speed, this.animationTime, this.map)

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
    val movement = Vector2.unit(LEFT) * speed * deltaTime
    this.position += movement
    if (this.carrying != null) {
      this.carrying.move(movement)
    }
    if (this.position.x < -(0.5 + size) * map.gridSize) {
      GameController.removeGameEntity(this)
    }
    nextImageTime -= deltaTime
  }

  override def render(): Unit = {
    if (nextImageTime <= 0) {
      currentImageIdx = (currentImageIdx + 1) % images.size
      nextImageTime = animationTime / images.size
    }
    for ((imageView, index) <- imageViews.zipWithIndex) {
      imageView.setX(position.x + (map.gridSize * index) - (map.gridSize / 2))
      imageView.setY(position.y - map.gridSize / 2)
      imageView.setImage(images(currentImageIdx))
    }
  }

  override def getCollisionBox: Box = new Box(
    position.x - map.gridSize / 2,
    position.y - map.gridSize / 2.1,
    map.gridSize + (size - 1) * map.gridSize,
    map.gridSize * 0.9
  )
}