package scalogger

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import scalogger.engine.{GameController, Input}

object Main {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends Application {

  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Scalogger")

    val root = new Pane()

    GameController.initialize(root)

    val scene = new Scene(root, 224, 240, Color.BLACK)

    Input.initialize(scene)

    primaryStage.setScene(scene)
    primaryStage.show()

    GameController.run()
  }
}
