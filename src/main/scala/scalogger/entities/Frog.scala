package scalogger.entities

import javafx.scene.image.ImageView
import scalogger.engine.Direction._
import scalogger.engine.Input.Button
import scalogger.engine.Resources.Sprite
import scalogger.engine.{GameEntity, Input, Movable, Vector2}

class Frog(private var initialPosition: Vector2,
           private var maxSpeed: Double,
           private var stepDistance: Int) extends GameEntity with Movable {

  private var position = initialPosition
  private var destinationPos = initialPosition

  private var jumping = false
  private var facingDirection = UP
  private val imageView = new ImageView()

  def setStepDistance(stepDistance: Int): Unit = {
    this.stepDistance = stepDistance
  }

  def jump(direction: Direction): Unit = {
    if (!jumping) {
      jumping = true
      destinationPos = position + Vector2.unit(direction) * stepDistance
      facingDirection = direction
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
    if (!jumping) {
      if (Input.getButtonDown(Button.MOVE_UP)) {
        this.jump(UP)
      } else if (Input.getButtonDown(Button.MOVE_DOWN)) {
        this.jump(DOWN)
      } else if (Input.getButtonDown(Button.MOVE_RIGHT)) {
        this.jump(RIGHT)
      } else if (Input.getButtonDown(Button.MOVE_LEFT)) {
        this.jump(LEFT)
      }
    }
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
    if (jumping) {
      imageView.setImage(Sprite.FROG_JUMPING)
    } else {
      imageView.setImage(Sprite.FROG_IDLE)
    }
    imageView.setX(position.x - stepDistance / 2)
    imageView.setY(position.y - stepDistance / 2)
    facingDirection match {
      case UP => imageView.setRotate(0)
      case DOWN => imageView.setRotate(180)
      case RIGHT => imageView.setRotate(90)
      case LEFT => imageView.setRotate(270)
    }
  }
}
