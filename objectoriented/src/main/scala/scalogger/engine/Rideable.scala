package scalogger.engine

abstract class Rideable extends GameEntity {
  protected var carrying: Rider = _

  def carry(rider: Rider): Unit = {
    this.carrying = rider
  }

  def drop(movable: Rider): Unit = {
    this.carrying = null
  }

  def getCollisionBox: Box
}
