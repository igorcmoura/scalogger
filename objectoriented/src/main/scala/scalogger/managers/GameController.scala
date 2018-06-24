package scalogger.managers

import javafx.animation.AnimationTimer
import javafx.scene.layout.Pane
import scalogger.engine._

import scala.collection.mutable
import scala.reflect.ClassTag

object GameController {

  private val gameEntities = new mutable.ListBuffer[GameEntity]()

  private var gameScreen: Pane = _

  private var lastTime: Long = 0
  private val animator = new AnimationTimer() {
    override def handle(now: Long): Unit = {
      val nowMilliSec = now / 1000000
      if (lastTime == 0) {
        lastTime = nowMilliSec
      }
      for (entity <- gameEntities) {
        entity.processInput()
      }
      Input.update()
      for (entity <- gameEntities) {
        entity.update(nowMilliSec - lastTime)
      }
      for (entity <- gameEntities) {
        entity.render()
      }
      lastTime = nowMilliSec
    }
  }

  def setGameScreen(pane: Pane): Unit = {
    gameScreen = pane
  }

  def clear(): Unit = {
    gameEntities.clear()
    gameScreen = null
    animator.stop()
    lastTime = 0
  }

  def addGameEntity(entity: GameEntity): Unit = {
    gameEntities += entity
    entity.attachToScreen(gameScreen)
  }

  def removeGameEntity(entity: GameEntity): Unit = {
    entity.detachFromScreen(gameScreen)
    gameEntities -= entity
  }

  def getGameEntities[T]()(implicit tag: ClassTag[T]): mutable.ListBuffer[T] = {
    val returnList = new mutable.ListBuffer[T]()
    for (entity <- gameEntities) {
      entity match {
        case t: T => returnList += t
        case _ =>
      }
    }
    returnList
  }

  def run(): Unit = {
    animator.start()
  }
}
