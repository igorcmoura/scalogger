package scalogger

import javafx.application.Application
import javafx.stage.Stage
import scalogger.scenes.MainGame

object Main {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends Application {

  override def start(stage: Stage): Unit = {
    val screenScale = 3

    stage.setTitle("Scalogger")

    val mainGame = new MainGame(screenScale)
    stage.setScene(mainGame.getScene)
    mainGame.run()
    stage.show()
  }
}
