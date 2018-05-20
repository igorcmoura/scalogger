package scalogger.engine

import javafx.animation.AnimationTimer
import javafx.scene.layout.Pane
import scalogger.entities.Frog

import scala.collection.mutable

object GameController {

  val gameEntities = new mutable.MutableList[GameEntity]()

  val gridSize = 16

  var lastTime: Long = 0

  def initialize(pane: Pane): Unit = {
    gameEntities += new Frog(new Vector2(7.5 * gridSize, 14.5 * gridSize), 0.2, gridSize)

    for (entity <- gameEntities) {
      pane.getChildren.add(entity.getImageView)
    }
  }

  def run(): Unit = {
    val animator = new AnimationTimer() {
      override def handle(now: Long): Unit = {
        val nowMilliSec = now / 1000000
        if (lastTime == 0) {
          lastTime = nowMilliSec
        }
        for (entity <- gameEntities) {
          entity.readInputs()
        }
        for (entity <- gameEntities) {
          entity.computePhysics(nowMilliSec - lastTime)
        }
        for (entity <- gameEntities) {
          entity.render()
        }
        lastTime = nowMilliSec
      }
    }
    animator.start()
  }
}
