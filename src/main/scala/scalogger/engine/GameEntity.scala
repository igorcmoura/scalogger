package scalogger.engine

trait GameEntity {
  def readInputs()
  def computePhysics(deltaTime: Double)
  def render()
}
