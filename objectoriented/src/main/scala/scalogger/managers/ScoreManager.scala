package scalogger.managers

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text}
import scalogger.engine.Observer
import scalogger.entities.FrogNotifier.Signals
import scalogger.entities.FrogNotifier.Signals.Signals
import scalogger.entities.{Frog, FrogNotifier, Goal}

class ScoreManager(gridSize: Int, frog: Frog, goals: Traversable[Goal]) {

  private var score = 0
  private val scoreText = new Text()

  initialize()

  private def initialize(): Unit = {
    scoreText.setFont(Font.font("Verdana", 10))
    scoreText.setFill(Color.WHITE)
    scoreText.setX(0.2 * gridSize)
    scoreText.setY(0.9 * gridSize)
    updateText()

    val frogObserver = new Observer[FrogNotifier.Signals.Signals] {
      override def onNotify(signal: Signals): Unit = signal match {
        case Signals.JUMP_UP => addScore(10)
        case Signals.JUMP_DOWN => addScore(-10)
        case _ =>
      }
    }
    frog.addObserver(frogObserver)

    val goalObserver = new Observer[Boolean] {
      override def onNotify(signal: Boolean): Unit = {
        if (signal) {
          addScore(50)
        }
      }
    }
    for (goal <- goals) {
      goal.addObserver(goalObserver)
    }
  }

  private def updateText(): Unit = {
    scoreText.setText("SCORE: " + score)
  }

  private def addScore(value: Int): Unit = {
    score += value
    this.updateText()
  }

  def render(screen: Pane): Unit = {
    screen.getChildren.add(scoreText)
  }
}
