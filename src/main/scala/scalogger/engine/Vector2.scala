package scalogger.engine

import Direction._

class Vector2(val x: Double, val y: Double) {

  def +(that: Vector2) = new Vector2(this.x + that.x, this.y + that.y)
  def -(that: Vector2) = new Vector2(this.x - that.x, this.y - that.y)

  def *(value: Double) = new Vector2(this.x * value, this.y * value)

  override def toString: String = "(" + x + ", " + y + ")"
}

object Vector2 {
  /**
    * Returns a unit vector for the given direction
    *
    * @param direction the screen direction
    * @return a unit vector for the given direction
    */
  def unit(direction: Direction): Vector2 = direction match {
    case UP => new Vector2(0, -1)
    case DOWN => new Vector2(0, 1)
    case RIGHT => new Vector2(1, 0)
    case LEFT => new Vector2(-1, 0)
  }
}
