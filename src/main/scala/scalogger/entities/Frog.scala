package scalogger.entities

import javafx.scene.image.ImageView
import scalogger.engine.Direction._
import scalogger.engine.Resources.Sprite
import scalogger.engine.{GameEntity, Movable, Vector2}

class Frog(private var initialPosition: Vector2,
           private var maxSpeed: Double,
           private var stepDistance: Int) extends GameEntity with Movable {

  private var position = initialPosition
  private var destinationPos = initialPosition

  private var jumping = false
  private val imageView = new ImageView()

  def setStepDistance(stepDistance: Int): Unit = {
    this.stepDistance = stepDistance
  }

  def jump(direction: Direction): Unit = {
    if (!jumping) {
      jumping = true
      destinationPos = position + Vector2.unit(direction) * stepDistance
      // TODO stop riding rideable
    }
  }

  override def getImageView: ImageView = imageView

  override def move(movement: Vector2): Unit = {
    position += movement
  }

  override def moveTo(position: Vector2): Unit = {
    this.position = position
  }

  override def readInputs(): Unit = {
    // TODO jump when jumping is false and reads a move input
  }

  override def computePhysics(deltaTime: Double): Unit = {
    // TODO get enemies on map and check collision

    if (jumping) {
      val direction = (destinationPos - position).unit
      val movement = direction * maxSpeed * deltaTime
      this.move(movement)

      // If direction changed then it passed the destination position
      if (direction != (destinationPos - position).unit) {
        this.moveTo(destinationPos)
      }

      if (position == destinationPos) {
        jumping = false
        // TODO get rideables on map and check collision
      }
    }
  }

  override def render(): Unit = {
    if (!jumping) {
      imageView.setImage(Sprite.FROG_IDLE)
    }
    imageView.setX(position.x - stepDistance / 2)
    imageView.setY(position.y - stepDistance / 2)
    // TODO render frog
  }
}
