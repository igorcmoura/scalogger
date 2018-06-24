package scalogger.scenes

import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text, TextAlignment}
import scalogger.ScenesCommunicator
import scalogger.managers.Constants.{SCREEN_HEIGHT, SCREEN_WIDTH}

class WelcomeScreen(screenScale: Int, scenesCommunicator: ScenesCommunicator) {

  private val scene = createScene()

  private def createScene(): Scene = {
    val screen = new StackPane()
    screen.setScaleX(screenScale)
    screen.setScaleY(screenScale)

    val startText = new Text("PRESS ANY BUTTON TO START")
    startText.setFont(Font.font("Verdana", 10))
    startText.setTextAlignment(TextAlignment.CENTER)
    startText.setFill(Color.WHITE)
    screen.getChildren.add(startText)

    val scene = new Scene(screen, SCREEN_WIDTH * screenScale, SCREEN_HEIGHT * screenScale, Color.BLACK)
    scene.setOnKeyPressed(new EventHandler[KeyEvent]() {
      override def handle(event: KeyEvent): Unit = {
        event.consume()
        scenesCommunicator.goToMainGame()
      }
    })
    scene
  }

  def getScene: Scene = scene
}
