package scalogger.managers

import javafx.animation.AnimationTimer
import javafx.scene.layout.Pane
import scalogger.engine.Direction.{LEFT, RIGHT}
import scalogger.engine._
import scalogger.entities._
import scalogger.managers.Resources.Sprite

import scala.collection.mutable
import scala.reflect.ClassTag

object GameController {

  private val gameEntities = new mutable.ListBuffer[GameEntity]()

  private var gameScreen: Pane = _
  private var map: GameMap = _
  private var scoreManager: ScoreManager = _

  def initialize(pane: Pane): Unit = {
    gameScreen = pane

    val gridSize = 16
    val mapWidth = 14
    val playableArea = new Box(0, 2, mapWidth, 13) * gridSize
    val waterArea = new Box(0, 1.2, mapWidth, 7) * gridSize
    map = new GameMap(gridSize, mapWidth, playableArea, waterArea)

    scoreManager = new ScoreManager(map)

    map.render(gameScreen)
    scoreManager.render(gameScreen)
    createSpawners()

    addGameEntity(new Frog(new Vector2(7.5 * map.gridSize, 14.5 * map.gridSize), 0.2, map))
  }

  private def createSpawners(): Unit = {
    val shortLog = new Log(new Vector2(-2.5 * map.gridSize, 6.5 * map.gridSize), 1, 0.007, map)
    val mediumLog = new Log(new Vector2(-3.5 * map.gridSize, 3.5 * map.gridSize), 2, 0.013, map)
    val longLog = new Log(new Vector2(-4.5 * map.gridSize, 4.5 * map.gridSize), 3, 0.045, map)

    val twoTurtles = new Turtles(new Vector2(14.5 * map.gridSize, 5.5 * map.gridSize), 2, 0.03, 1000, map)
    val threeTurtles = new Turtles(new Vector2(14.5 * map.gridSize, 7.5 * map.gridSize), 3, 0.03, 1000, map)

    val car = new Car(new Vector2(14.5 * map.gridSize, 11.5 * map.gridSize), new CarType(Sprite.CAR, 1, 0.013, LEFT), map)
    val greySportCar = new Car(new Vector2(-0.5 * map.gridSize, 10.5 * map.gridSize), new CarType(Sprite.SPORTCAR_GREY, 1, 0.02, RIGHT), map)
    val yellowSportCar = new Car(new Vector2(14.5 * map.gridSize, 13.5 * map.gridSize), new CarType(Sprite.SPORTCAR_YELLOW, 1, 0.01, LEFT), map)
    val truck = new Car(new Vector2(14.5 * map.gridSize, 9.5 * map.gridSize), new CarType(Sprite.TRUCK, 2, 0.03, LEFT), map)
    val bulldozer = new Car(new Vector2(-0.5 * map.gridSize, 12.5 * map.gridSize), new CarType(Sprite.BULLDOZER, 1, 0.01, RIGHT), map)

    addGameEntity(new Spawner(mediumLog, 6000, 9000))
    addGameEntity(new Spawner(longLog, 2000, 3000))
    addGameEntity(new Spawner(twoTurtles, 2000, 3000))
    addGameEntity(new Spawner(shortLog, 10000, 15000))
    addGameEntity(new Spawner(threeTurtles, 2000, 3000))

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