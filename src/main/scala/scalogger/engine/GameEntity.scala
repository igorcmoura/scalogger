package scalogger.engine

trait GameEntity {
  def readInputs()
  def updatePhysics(deltaTime: Double)
  def render()
}
