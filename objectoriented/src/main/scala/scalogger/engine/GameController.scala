package scalogger.engine

import javafx.animation.AnimationTimer
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text}
import scalogger.engine.Resources.Sprite
import scalogger.entities.Frog

import scala.collection.mutable

object GameController {

  private val gameEntities = new mutable.ListBuffer[GameEntity]()

  private val gridSize = 16
  private val gridWidth = 14

  private var gameScreen: Pane = _

  private var score: Long = 0

  def initialize(pane: Pane): Unit = {
    gameScreen = pane
    val scoreText = new Text()

    this.initScoreText(scoreText)
    pane.getChildren.add(scoreText)

    renderMap()

    val gridArea = new Box(0, 2, gridWidth, 13) * gridSize
    addGameEntity(new Frog(new Vector2(7.5 * gridSize, 14.5 * gridSize), 0.2, gridSize, gridArea))
  }

  private def addScore(value: Long): Unit = {
    score += value
  }

  private def initScoreText(scoreText: Text): Unit = {
    scoreText.setText("SCORE: " + score.toString)
    scoreText.setFont(Font.font("Verdana", 20))
    scoreText.setFill(Color.WHITE)
    scoreText.setX(1 * gridSize)
    scoreText.setY(1 * gridSize)
  }

  private def renderMap(): Unit = {
    val water = (new Box(0, 1.2, gridWidth, 7) * gridSize).toRectangle
    water.setFill(Color.DARKBLUE)
    gameScreen.getChildren.add(water)

    this.renderSideWalk(8)
    this.renderSideWalk(14)

    val finalView = new ImageView(Sprite.FINAL)
    finalView.setFitWidth(gridSize * gridWidth)
    finalView.setFitHeight(gridSize * 2)
    finalView.setX(0)
    finalView.setY(1 * gridSize)
    gameScreen.getChildren.add(finalView)
  }

  private def renderSideWalk(lane: Double): Unit = {
    for (i <- 0 until gridWidth) {
      val sideWalkView = new ImageView(Sprite.SIDEWALK)
      sideWalkView.setFitWidth(gridSize)
      sideWalkView.setFitHeight(gridSize)
      sideWalkView.setX(i * gridSize)
      sideWalkView.setY(lane * gridSize)
      gameScreen.getChildren.add(sideWalkView)
    }
  }

  def addGameEntity(entity: GameEntity): Unit = {
    gameEntities += entity
    entity.attachToScreen(gameScreen)
  }

  def removeGameEntity(entity: GameEntity): Unit ={
    entity.detachFromScreen(gameScreen)
    gameEntities -= entity
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
