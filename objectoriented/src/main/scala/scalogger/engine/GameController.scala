package scalogger.engine

import javafx.animation.AnimationTimer
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text}
import scalogger.engine.Direction.{LEFT, RIGHT}
import scalogger.engine.Resources.Sprite
import scalogger.entities.{Car, CarType, Frog}

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
    createSpawners()

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

  private def createSpawners(): Unit = {
    val truck = new Car(new Vector2(14.5 * gridSize, 9.5 * gridSize), new CarType(Sprite.TRUCK, 2, 0.03, LEFT), gridSize)
    val greySportCar = new Car(new Vector2(-0.5 * gridSize, 10.5 * gridSize), new CarType(Sprite.SPORTCAR_GREY, 1, 0.02, RIGHT), gridSize)
    val car = new Car(new Vector2(14.5 * gridSize, 11.5 * gridSize), new CarType(Sprite.CAR, 1, 0.013, LEFT), gridSize)
    val bulldozer = new Car(new Vector2(-0.5 * gridSize, 12.5 * gridSize), new CarType(Sprite.BULLDOZER, 1, 0.01, RIGHT), gridSize)
    val yellowSportCar = new Car(new Vector2(14.5 * gridSize, 13.5 * gridSize), new CarType(Sprite.SPORTCAR_YELLOW, 1, 0.01, LEFT), gridSize)

    addGameEntity(new Spawner(truck, 3000, 6000))
    addGameEntity(new Spawner(greySportCar, 10000, 12000))
    addGameEntity(new Spawner(car, 4000, 8000))
    addGameEntity(new Spawner(bulldozer, 6000, 9000))
    addGameEntity(new Spawner(yellowSportCar, 6000, 9000))
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
    animator.start()
  }
}
