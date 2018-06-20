package scalogger

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import scalogger.managers.{GameController, Input}

object Main {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends Application {

  private val WIDTH = 224
  private val HEIGHT = 256

  override def start(primaryStage: Stage): Unit = {
    val screenScale = 3

    primaryStage.setTitle("Scalogger")

    val root = new Pane()
    root.setScaleX(screenScale)
    root.setScaleY(screenScale)
    val panePosition = screenScale * (screenScale - 1) / 2 // For some reason, when scaled, the pane moves on an arithmetic progressive distance
    root.setTranslateX(WIDTH * panePosition)
    root.setTranslateY(HEIGHT * panePosition)

    GameController.initialize(root)

    val scene = new Scene(root, WIDTH * screenScale, HEIGHT * screenScale, Color.BLACK)
    Input.initialize(scene)

    primaryStage.setScene(scene)
    primaryStage.show()

    GameController.run()
  }
}
