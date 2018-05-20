package scalogger.engine

import Direction._

class Vector2(val x: Double, val y: Double) {

  def +(that: Vector2) = new Vector2(this.x + that.x, this.y + that.y)
  def -(that: Vector2) = new Vector2(this.x - that.x, this.y - that.y)
  def *(value: Double) = new Vector2(this.x * value, this.y * value)
  def /(value: Double) = new Vector2(this.x / value, this.y / value)
  lazy val unary_- = new Vector2(-this.x, -this.y)

  lazy val length: Double = math.sqrt(math.pow(this.x, 2) + math.pow(this.y, 2))
  lazy val unit: Vector2 = this/this.length

  override def toString: String = "(" + x + ", " + y + ")"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Vector2]

  override def equals(other: Any): Boolean = other match {
    case that: Vector2 =>
      (that canEqual this) &&
        x == that.x &&
        y == that.y
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(x, y)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
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
