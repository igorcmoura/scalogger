package scalogger.engine

import javafx.scene.layout.Pane
import scalogger.managers.GameController

import scala.util.Random

class Spawner[T <: GameEntity](private var model: Spawnable[T], private var minInterval: Double, private var maxInterval: Double) extends GameEntity {
  private val rand = Random
  private var untilSpawn: Double = 0

  private def spawn(): Unit = {
    GameController.addGameEntity(model.spawn())
  }

  private def getInterval = minInterval + (rand.nextDouble() * (maxInterval - minInterval))

  override def attachToScreen(screen: Pane): Unit = {}

  override def detachFromScreen(screen: Pane): Unit = {}

  override def update(deltaTime: Double): Unit = {
    untilSpawn -= deltaTime
    if (untilSpawn <= 0) {
      spawn()
      untilSpawn = getInterval
    }
  }
}
