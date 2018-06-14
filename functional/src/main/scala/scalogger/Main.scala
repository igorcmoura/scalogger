package scalogger

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color

object Main extends JFXApp {
  val screenScale = 3
  stage = new JFXApp.PrimaryStage {
    title.value = "Scalogger"
    width = 224*screenScale
    height = 256*screenScale
    scene = new Scene {
      fill = Color.Black
      content = new Pane()
    }
  }
}
