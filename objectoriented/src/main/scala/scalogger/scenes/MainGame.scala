package scalogger.scenes

import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import scalogger.ScenesCommunicator
import scalogger.engine.Direction.{LEFT, RIGHT}
import scalogger.engine.{Box, Spawner, Vector2}
import scalogger.entities._
import scalogger.managers.Constants.{SCREEN_HEIGHT, SCREEN_WIDTH}
import scalogger.managers.Resources.Sprite
import scalogger.managers.{GameController, GameMap, Input, ScoreManager}

class MainGame(screenScale: Int, scenesCommunicator: ScenesCommunicator) {
  private val scene = createScene()

  def getScene: Scene = this.scene

  def run(): Unit = {
    GameController.run()
  }

  private def createScene(): Scene = {
    GameController.clear()

    val gameScreen = new Pane()
    gameScreen.setScaleX(screenScale)
    gameScreen.setScaleY(screenScale)
    val panePosition = screenScale * (screenScale - 1) / 2 // For some reason, when scaled, the pane moves on an arithmetic progressive distance
    gameScreen.setTranslateX(SCREEN_WIDTH * panePosition)
    gameScreen.setTranslateY(SCREEN_HEIGHT * panePosition)

    val gridSize = 16
    val mapWidth = 14
    val playableArea = new Box(0, 2, mapWidth, 13) * gridSize
    val waterArea = new Box(0, 1.2, mapWidth, 7) * gridSize
    val map = new GameMap(gridSize, mapWidth, playableArea, waterArea)

    val frog = new Frog(new Vector2(7.5 * map.gridSize, 14.5 * map.gridSize), 0.2, map)

    val scoreManager = new ScoreManager(gridSize, frog)

    map.render(gameScreen)
    scoreManager.render(gameScreen)
    createSpawners(map)

    GameController.setGameScreen(gameScreen)
    GameController.addGameEntity(frog)

    val scene = new Scene(gameScreen, SCREEN_WIDTH * screenScale, SCREEN_HEIGHT * screenScale, Color.BLACK)
    Input.initialize(scene)
    GameController.setGameScreen(gameScreen)
    scene
  }

  private def createSpawners(map: GameMap): Unit = {
    val shortLog = new Log(new Vector2(-2.5 * map.gridSize, 6.5 * map.gridSize), 1, 0.007, map)
    val mediumLog = new Log(new Vector2(-3.5 * map.gridSize, 3.5 * map.gridSize), 2, 0.013, map)
    val longLog = new Log(new Vector2(-4.5 * map.gridSize, 4.5 * map.gridSize), 3, 0.045, map)

    val twoTurtles = new Turtles(new Vector2(14.5 * map.gridSize, 5.5 * map.gridSize), 2, 0.03, 0.2, 400, map)
    val threeTurtles = new Turtles(new Vector2(14.5 * map.gridSize, 7.5 * map.gridSize), 3, 0.03, 0.2, 400, map)

    val car = new Car(new Vector2(14.5 * map.gridSize, 11.5 * map.gridSize), new CarType(Sprite.CAR, 1, 0.013, LEFT), map)
    val greySportCar = new Car(new Vector2(-0.5 * map.gridSize, 10.5 * map.gridSize), new CarType(Sprite.SPORTCAR_GREY, 1, 0.02, RIGHT), map)
    val yellowSportCar = new Car(new Vector2(14.5 * map.gridSize, 13.5 * map.gridSize), new CarType(Sprite.SPORTCAR_YELLOW, 1, 0.01, LEFT), map)
    val truck = new Car(new Vector2(14.5 * map.gridSize, 9.5 * map.gridSize), new CarType(Sprite.TRUCK, 2, 0.03, LEFT), map)
    val bulldozer = new Car(new Vector2(-0.5 * map.gridSize, 12.5 * map.gridSize), new CarType(Sprite.BULLDOZER, 1, 0.01, RIGHT), map)

    GameController.addGameEntity(new Spawner(mediumLog, 6000, 9000))
    GameController.addGameEntity(new Spawner(longLog, 2000, 3000))
    GameController.addGameEntity(new Spawner(twoTurtles, 2000, 3000))
    GameController.addGameEntity(new Spawner(shortLog, 10000, 15000))
    GameController.addGameEntity(new Spawner(threeTurtles, 2000, 3000))

    GameController.addGameEntity(new Spawner(truck, 3000, 6000))
    GameController.addGameEntity(new Spawner(greySportCar, 10000, 12000))
    GameController.addGameEntity(new Spawner(car, 4000, 8000))
    GameController.addGameEntity(new Spawner(bulldozer, 6000, 9000))
    GameController.addGameEntity(new Spawner(yellowSportCar, 6000, 9000))
  }
}
