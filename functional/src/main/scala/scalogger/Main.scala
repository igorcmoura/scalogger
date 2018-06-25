package scalogger

import scalafx.application.JFXApp
import scalogger.scenes.{MainGame, WelcomeScreen}

object Main extends JFXApp {
  val screenScale = 3

  stage = new JFXApp.PrimaryStage {
    title.value = "Scalogger"

    def goToWelcomeScreen(): Unit = {
      val welcomeScreen = new WelcomeScreen(screenScale, goToMainGame)
      scene = welcomeScreen.getScene
    }

    def goToMainGame(): Unit = {
      val mainGame = new MainGame(screenScale, goToWelcomeScreen)
      scene = mainGame.getScene
    }
    goToWelcomeScreen()
  }
}
