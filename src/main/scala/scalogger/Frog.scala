package scalogger

import scalogger.engine.Direction._
import scalogger.engine.{GameEntity, Movable, Vector2}

class Frog(private var initialPosition: Vector2,
           private var maxSpeed: Double,
           private var stepDistance: Int) extends GameEntity with Movable {

  private var position = initialPosition
  private var destinationPos = initialPosition

  private var jumping = false

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

  override def move(movement: Vector2): Unit = {
    position += movement
  }

  override def moveTo(position: Vector2): Unit = {
    this.position = position
  }

  override def readInputs(): Unit = {
    // TODO jump when jumping is false and reads a move input
  }

  override def updatePhysics(deltaTime: Double): Unit = {
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
    // TODO render frog
  }
}
