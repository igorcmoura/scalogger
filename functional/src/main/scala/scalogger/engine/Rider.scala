package scalogger.engine

trait Rider extends Movable {
  def ride(rideable: Rideable)
  def getOff()
}
