package scalogger.engine

import javafx.animation.AnimationTimer
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.scene.text.Font
import scalogger.engine.Resources.Sprite
import scalogger.entities.Frog

import scala.collection.mutable

object GameController {

  private val gameEntities = new mutable.MutableList[GameEntity]()
  private val gridWidth = 14

  private var score: Long = 0

  def initialize(pane: Pane, screenScale: Int): Unit = {
    val gridSize = 16 * screenScale
    val gridArea = new Box(0, 2, gridWidth, 13) * gridSize
    val scoreText = new Text()

    this.initScoreText(scoreText, gridSize)
    pane.getChildren.add(scoreText)

    renderMap(pane, gridSize)

    gameEntities += new Frog(new Vector2(7.5 * gridSize, 14.5 * gridSize), 0.2 * screenScale, gridSize, gridArea)

    for (entity <- gameEntities) {
      val imageView = entity.getImageView
      pane.getChildren.add(imageView)
      imageView.setFitWidth(gridSize)
      imageView.setFitHeight(gridSize)
    }
  }

  private def addScore(value: Long): Unit = {
    score += value
  }

  private def initScoreText(scoreText: Text, gridSize: Int): Unit = {
    scoreText.setText("SCORE: " + score.toString)
    scoreText.setFont(Font.font ("Verdana", 20))
    scoreText.setFill(Color.WHITE)
    scoreText.setX(1*gridSize)
    scoreText.setY(1*gridSize)
  }

  private def renderMap(pane: Pane, gridSize: Double): Unit = {
    val water = (new Box(0, 1.2, gridWidth, 7) * gridSize).toRectangle
    water.setFill(Color.DARKBLUE)
    pane.getChildren.add(water)

    this.renderSideWalk(pane, gridSize, 8)
    this.renderSideWalk(pane, gridSize, 14)

    val finalView = new ImageView(Sprite.FINAL)
    finalView.setFitWidth(gridSize * gridWidth)
    finalView.setFitHeight(gridSize * 2)
    finalView.setX(0)
    finalView.setY(1 * gridSize)
    pane.getChildren.add(finalView)
  }

  private def renderSideWalk(pane: Pane, gridSize: Double, lane: Double): Unit = {
    for (i <- 0 to gridWidth) {
      val sideWalkView = new ImageView(Sprite.SIDEWALK)
      sideWalkView.setFitWidth(gridSize)
      sideWalkView.setFitHeight(gridSize)
      sideWalkView.setX(i * gridSize)
      sideWalkView.setY(lane * gridSize)
      pane.getChildren.add(sideWalkView)
    }
  }

  def run(): Unit = {
    var lastTime: Long = 0

    val animator = new AnimationTimer() {
      override def handle(now: Long): Unit = {
        val nowMilliSec = now / 1000000
        if (lastTime == 0) {
          lastTime = nowMilliSec
        }
        for (entity <- gameEntities) {
          entity.readInputs()
        }
        Input.update()
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
