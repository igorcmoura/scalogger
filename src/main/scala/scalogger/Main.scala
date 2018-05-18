package scalogger

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.text.Text

object Main extends JFXApp {
  stage = new JFXApp.PrimaryStage()
  stage.title.value = "Scalogger"

  val scene = new Scene()
  scene.fill = Black
  val content = new HBox()
  val screenText = new Text()
  screenText.text = "Welcome to Scalogger!"
  screenText.fill = LightGreen
  screenText.style = "-fx-font-size: 48pt"
  content.children = screenText
  scene.content = content

  stage.scene = scene
}
