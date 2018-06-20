package scalogger.managers

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text}

class ScoreManager(map: GameMap) {

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
}
