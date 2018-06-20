package scalogger.engine

import javafx.scene.shape.Rectangle

class Box(val x: Double, val y: Double, val width: Double, val height: Double) {
  def this(x: Double, y: Double, size: Double) = this(x, y, size, size)

  def +(value: Double) = new Box(this.x - value, this.y - value, this.width + value * 2, this.height + value * 2)
  def -(value: Double) = new Box(this.x + value, this.y + value, this.width - value * 2, this.height - value * 2)
  def *(value: Double) = new Box(this.x * value, this.y * value, this.width * value, this.height * value)

  def collidesWith(other: Box): Boolean =
    new Vector2(x, y).isInside(other) ||
      new Vector2(x + width, y).isInside(other) ||
      new Vector2(x, y + height).isInside(other) ||
      new Vector2(x + width, y + height).isInside(other)

  def toRectangle = new Rectangle(this.x, this.y, this.width, this.height)
}
