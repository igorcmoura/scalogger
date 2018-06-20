package scalogger.managers

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text}
import scalogger.engine.Observer
import scalogger.entities.FrogNotifier
import scalogger.entities.FrogNotifier.Signals
import scalogger.entities.FrogNotifier.Signals.Signals

class ScoreManager(map: GameMap) extends Observer[FrogNotifier.Signals.Signals] {

  private var score = 0

  private val scoreText = new Text()
  scoreText.setFont(Font.font("Verdana", 20))
  scoreText.setFill(Color.WHITE)
  scoreText.setX(0)
  scoreText.setY(1 * map.gridSize)
  updateText()

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

  override def onNotify(signal: Signals): Unit = {
    signal match {
      case Signals.JUMP_UP => addScore(10)
      case Signals.JUMP_DOWN => addScore(-10)
      case _ =>
    }
  }
}
