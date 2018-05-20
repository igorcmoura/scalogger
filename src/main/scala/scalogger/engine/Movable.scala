package scalogger.engine

trait Movable {
  def move(movement: Vector2)
  def moveTo(position: Vector2)
}
