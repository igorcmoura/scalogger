package scalogger

import javafx.application.Application
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.scene.{Group, Scene}
import javafx.stage.Stage

object Main {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends Application {

  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Scalogger")

    val root = new Group()
    val scene = new Scene(root, Color.BLACK)
    primaryStage.setScene(scene)

    val screenText = new Text("Welcome to Scalogger!")
    screenText.setFill(Color.LIGHTGREEN)
    screenText.setStyle("-fx-font-size: 48pt")
    val content = new HBox(screenText)
    root.getChildren.add(content)

    primaryStage.show()
  }
}
