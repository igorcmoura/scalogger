package scalogger.scenes

import javafx.scene.input.KeyEvent
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, Text}
import scalogger.managers.Constants.{SCREEN_HEIGHT, SCREEN_WIDTH}

class WelcomeScreen(screenScale: Int, goToMainGame: () => Unit) {
  private val scene = createScene()

  private def createScene(): Scene = new Scene(SCREEN_WIDTH * screenScale, SCREEN_HEIGHT * screenScale) {
    fill = Color.Black

    content = new Pane {
      scaleX = screenScale
      scaleY = screenScale
      translateX = 95 * (screenScale - 1)
      translateY = 60 * (screenScale - 1)

      children = Seq(
        new Text("PRESS ANY BUTTON TO START") {
          font = Font.font("Verdana", 10)
          fill = Color.White
          x = SCREEN_WIDTH / 2 - (layoutBounds.value.getWidth / 2)
          y = SCREEN_HEIGHT / 2 - (layoutBounds.value.getHeight / 2)
        }
      )
    }

    onKeyPressed = (event: KeyEvent) => {
      event.consume()
      goToMainGame()
    }
  }

  def getScene: Scene = scene
}
