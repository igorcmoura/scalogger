package scalogger.entities

import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import scalogger.engine.Direction._
import scalogger.engine._
import scalogger.managers.Input.Button
import scalogger.managers.Resources.Sprite
import scalogger.managers.{GameController, GameMap, Input}

class Frog(initialPosition: Vector2,
           private var maxSpeed: Double,
           private var map: GameMap) extends GameEntity with Rider {

  private var position = initialPosition
  private var destinationPos = initialPosition
  private var stepDistance = map.gridSize

  private val notifier = new FrogNotifier

  private var riding: Rideable = _

  private val deathCounterInit: Double = 2000
  private var deathCounter = deathCounterInit

  private var facingDirection = UP
  private val imageView = new ImageView()
  imageView.setFitWidth(map.gridSize)
  imageView.setFitHeight(map.gridSize)

  object State extends Enumeration {
    type State = Value
    val IDLE, JUMPING, DEAD, DROWNED = Value
  }
  private var state = State.IDLE

  override def attachToScreen(screen: Pane): Unit = {
    screen.getChildren.add(this.imageView)
  }

  override def detachFromScreen(screen: Pane): Unit = {
    screen.getChildren.remove(this.imageView)
  }

  def addObserver(observer: Observer[FrogNotifier.Signals.Signals]): Unit = {
    notifier.addObserver(observer)
  }

  def getCollisionBox: Box = new Box(
    position.x - (0.75 * map.gridSize / 2),
    position.y - (0.75 * map.gridSize / 2),
    0.75 * map.gridSize,
    0.75 * map.gridSize
  )

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
      getOff()
    }
  }

  def die(): Unit = {
    state = State.DEAD
    notifier.notifyDeath()
  }

  def drown(): Unit = {
    state = State.DROWNED
    notifier.notifyDeath()
  }

  def ride(rideable: Rideable): Unit = {
    getOff()
    this.riding = rideable
    rideable.carry(this)
  }

  override def getOff(): Unit = {
    if (this.riding == null) return
    this.riding.drop(this)
    this.riding = null
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
        die()
      }
    }

    if (!this.position.isInside(map.playableArea)) {
      getOff()
      die()
    }

    state match {
      case State.IDLE => {
        // If it's on the water and not riding anything, kills the frog
        if (this.position.isInside(map.waterArea) && this.riding == null) {
          drown()
        }
      }

      case State.JUMPING => {
        val direction = (destinationPos - position).unit
        val movement = direction * maxSpeed * deltaTime
        this.move(movement)

        // If direction changed then it surpassed the destination position
        if (direction != (destinationPos - position).unit) {
          this.moveTo(destinationPos)
        }

        if (position == destinationPos) {
          state = State.IDLE
          notifier.notifyJump(facingDirection)

          // If not riding anything, check if there's something to ride
          if (this.riding == null) {
            val rideables = GameController.getGameEntities[Rideable]()
            for (rideable <- rideables) {
              if (rideable.getCollisionBox.collidesWith(this.getCollisionBox)) {
                ride(rideable)
              }
            }
          }
        }
      }

      case State.DEAD | State.DROWNED => {
        deathCounter -= deltaTime
        if (deathCounter < 0) {
          this.position = initialPosition
          this.state = State.IDLE
          this.deathCounter = deathCounterInit
        }
      }
    }
  }

  override def render(): Unit = {
    state match {
      case State.IDLE => imageView.setImage(Sprite.FROG_IDLE)
      case State.JUMPING => imageView.setImage(Sprite.FROG_JUMPING)
      case State.DEAD => {
        val index: Int = if (deathCounter <= 0) 3 else (4 - (deathCounter * 4 / deathCounterInit)).toInt
        facingDirection = UP
        imageView.setImage(Seq(Sprite.FROG_DEATH_1, Sprite.FROG_DEATH_2, Sprite.FROG_DEATH_3, Sprite.SKULL)(index))
      }
      case State.DROWNED => {
        val index: Int = if (deathCounter <= 0) 3 else (4 - (deathCounter * 4 / deathCounterInit)).toInt
        facingDirection = UP
        imageView.setImage(Seq(Sprite.FROG_DROWNING_1, Sprite.FROG_DROWNING_2, Sprite.FROG_DROWNING_3, Sprite.SKULL)(index))
      }
    }
    imageView.toFront()
    imageView.setX(position.x - map.gridSize / 2)
    imageView.setY(position.y - map.gridSize / 2)
    facingDirection match {
      case UP => imageView.setRotate(0)
      case DOWN => imageView.setRotate(180)
      case RIGHT => imageView.setRotate(90)
      case LEFT => imageView.setRotate(270)
    }
  }
}
