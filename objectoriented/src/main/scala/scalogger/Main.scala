package scalogger

import javafx.application.Application
import javafx.stage.Stage
import scalogger.scenes.{MainGame, WelcomeScreen}

object Main {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends Application {

  override def start(stage: Stage): Unit = {
    val screenScale = 3

    stage.setTitle("Scalogger")

    val scenesCommunicator = new ScenesCommunicator {
      override def goToWelcomeScreen(): Unit = {
        val welcomeScreen = new WelcomeScreen(screenScale, this)
        stage.setScene(welcomeScreen.getScene)
      }

      override def goToMainGame(): Unit = {
        val mainGame = new MainGame(screenScale, this)
        stage.setScene(mainGame.getScene)
        mainGame.run()
      }
    }
    scenesCommunicator.goToWelcomeScreen()
    stage.show()
  }
}

trait ScenesCommunicator {
  def goToWelcomeScreen()
  def goToMainGame()
}
