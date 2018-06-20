package scalogger.entities

import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import scalogger.engine.Direction._
import scalogger.engine.Input.Button
import scalogger.engine.Resources.Sprite
import scalogger.engine._

class Frog(initialPosition: Vector2,
           private var maxSpeed: Double,
           private var map: GameMap) extends GameEntity with Movable {

  private var position = initialPosition
  private var destinationPos = initialPosition
  private var stepDistance = map.gridSize

  private var riding: Rideable = _

  private var facingDirection = UP
  private val imageView = new ImageView()
  imageView.setFitWidth(map.gridSize)
  imageView.setFitHeight(map.gridSize)

  object State extends Enumeration {
    type State = Value
    val IDLE, JUMPING = Value
  }
  private var state = State.IDLE

  override def attachToScreen(screen: Pane): Unit = {
    screen.getChildren.add(this.imageView)
  }

  override def detachFromScreen(screen: Pane): Unit = {
    screen.getChildren.remove(this.imageView)
  }

  def getCollisionBox: Box = new Box(position.x - map.gridSize / 2, position.y - map.gridSize / 2, map.gridSize, map.gridSize)

  def setStepDistance(stepDistance: Int): Unit = {
    this.stepDistance = stepDistance
  }

  def jump(direction: Direction): Unit = {
    if (state == State.IDLE) {
      destinationPos = position + Vector2.unit(direction) * stepDistance
      if (!destinationPos.isInside(map.playableArea)) {
        return
      }
      facingDirection = direction
      state = State.JUMPING
      // If riding something, stop riding it
      if (this.riding != null) {
        this.riding.unride(this)
        this.riding = null
      }
    }
  }

  override def move(movement: Vector2): Unit = {
    position += movement
  }

  override def moveTo(position: Vector2): Unit = {
    this.position = position
  }

  override def processInput(): Unit = {
    if (state == State.IDLE) {
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

  override def update(deltaTime: Double): Unit = {
    val enemies = GameController.getGameEntities[Car]()
    for (enemy <- enemies) {
      if (enemy.getCollisionBox.collidesWith(this.getCollisionBox)) {
        // TODO kill frog
        println("Collided")
      }
    }

    if (state == State.JUMPING) {
      val direction = (destinationPos - position).unit
      val movement = direction * maxSpeed * deltaTime
      this.move(movement)

      // If direction changed then it surpassed the destination position
      if (direction != (destinationPos - position).unit) {
        this.moveTo(destinationPos)
      }

      if (position == destinationPos) {
        state = State.IDLE

        // If not riding anything, check if there's something to ride
        if (this.riding == null) {
          val rideables = GameController.getGameEntities[Rideable]()
          for (rideable <- rideables) {
            if (rideable.getCollisionBox.collidesWith(this.getCollisionBox)) {
              if (this.riding != null) {
                this.riding.unride(this)
              }

              this.riding = rideable
              rideable.ride(this)
            }
          }
        }
      }
    }
  }

  override def render(): Unit = {
    if (state == State.JUMPING) {
      imageView.setImage(Sprite.FROG_JUMPING)
    } else {
      imageView.setImage(Sprite.FROG_IDLE)
    }
    imageView.toFront()
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
