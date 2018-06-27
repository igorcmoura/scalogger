package scalogger.engine

import scalafx.scene.shape.Rectangle

class Box(val x: Double, val y: Double, val width: Double, val height: Double) {
  def this(x: Double, y: Double, size: Double) = this(x, y, size, size)

  def +(value: Double) = new Box(this.x - value, this.y - value, this.width + value * 2, this.height + value * 2)
  def -(value: Double) = new Box(this.x + value, this.y + value, this.width - value * 2, this.height - value * 2)
  def *(value: Double) = new Box(this.x * value, this.y * value, this.width * value, this.height * value)

  def middle = new Vector2(x + (width / 2), y + (height / 2))

  def collidesWith(other: Box): Boolean = !(isAbove(other) || isBelow(other) || isAtTheLeft(other) || isAtTheRight(other))

  private def isAbove(other: Box) = this.y + height < other.y
  private def isBelow(other: Box) = this.y > other.y + other.height
  private def isAtTheLeft(other: Box) = this.x + width < other.x
  private def isAtTheRight(other: Box) = this.x > other.x + other.width

  def toRectangle = Rectangle(this.x, this.y, this.width, this.height)
}
