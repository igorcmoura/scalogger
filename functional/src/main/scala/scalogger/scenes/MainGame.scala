package scalogger.scenes

import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalogger.managers.Constants.{SCREEN_HEIGHT, SCREEN_WIDTH}

class MainGame(screenScale: Int, goToWelcomeScreen: () => Unit) {
  def getScene: Scene = new Scene(SCREEN_WIDTH * screenScale, SCREEN_HEIGHT * screenScale) {
    fill = Color.Green
  }
}
