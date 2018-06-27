package scalogger.entities

import scalogger.engine.Direction._
import scalogger.engine.Observable
import scalogger.entities.FrogNotifier.Signals.{JUMP_UP, JUMP_DOWN, JUMP_LEFT, JUMP_RIGHT, DEATH}

object FrogNotifier {
  object Signals extends Enumeration {
    type Signals = Value
    val DEATH, JUMP_UP, JUMP_DOWN, JUMP_LEFT, JUMP_RIGHT = Value
  }
}

class FrogNotifier extends Observable[FrogNotifier.Signals.Signals] {
  def notifyJump(direction: Direction): Unit = {
    direction match {
      case UP => super.notify(JUMP_UP)
      case DOWN => super.notify(JUMP_DOWN)
      case LEFT => super.notify(JUMP_LEFT)
      case RIGHT => super.notify(JUMP_RIGHT)
    }
  }

  def notifyDeath(): Unit = {
    super.notify(DEATH)
  }
}
