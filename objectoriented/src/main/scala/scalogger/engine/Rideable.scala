package scalogger.engine

abstract class Rideable extends GameEntity {
  protected var carrying: Movable = _

  def ride(movable: Movable): Unit = {
    this.carrying = movable
  }

  def unride(movable: Movable): Unit = {
    this.carrying = null
  }

  def getCollisionBox: Box
}
