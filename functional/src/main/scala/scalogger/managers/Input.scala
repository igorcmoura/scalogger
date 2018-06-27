package scalogger.managers

import javafx.event.{EventHandler, EventType}
import javafx.scene.Scene
import javafx.scene.input.{KeyCode, KeyEvent}

import scala.collection.mutable

object Input {
  // Map from Button to tuple containing this iteration state and last iteration state
  private val buttonsState = new mutable.HashMap[Button.Value, (EventType[KeyEvent], EventType[KeyEvent])]()

  def initialize(targetScene: Scene): Unit = {
    for (button <- Button.values) {
      buttonsState += (button -> (KeyEvent.KEY_RELEASED, KeyEvent.KEY_RELEASED))
    }

    val keyboardHandler = new EventHandler[KeyEvent]() {
      override def handle(event: KeyEvent): Unit = {
        val button = Button.fromKeyCode(event.getCode).orNull
        if (button == null) {
          return
        }
        val (currentState, _) = buttonsState(button)
        buttonsState.update(button, (event.getEventType, currentState))
        event.consume()
      }
    }
    targetScene.setOnKeyPressed(keyboardHandler)
    targetScene.setOnKeyReleased(keyboardHandler)
  }

  def update(): Unit = {
    for ((button, (currentState, _)) <- buttonsState) {
      buttonsState.update(button, (currentState, currentState))
    }
  }

  def getButtonDown(button: Button.Value): Boolean = {
    val (currentState, lastState) = buttonsState(button)
    currentState == KeyEvent.KEY_PRESSED && lastState == KeyEvent.KEY_RELEASED
  }

  def getButton(button: Button.Value): Boolean = {
    val (currentState, _) = buttonsState(button)
    currentState == KeyEvent.KEY_PRESSED
  }

  def getButtonUp(button: Button.Value): Boolean = {
    val (currentState, lastState) = buttonsState(button)
    currentState == KeyEvent.KEY_RELEASED && lastState == KeyEvent.KEY_PRESSED
  }

  object Button extends Enumeration {
    protected case class Val(keyCode: KeyCode) extends super.Val
    implicit def valueToButtonVal(x: Value): Val = x.asInstanceOf[Val]
    type Button = Value
    val MOVE_UP = Val(KeyCode.UP)
    val MOVE_DOWN = Val(KeyCode.DOWN)
    val MOVE_RIGHT = Val(KeyCode.RIGHT)
    val MOVE_LEFT = Val(KeyCode.LEFT)

    def fromKeyCode(keyCode: KeyCode): Option[Input.Button.Value] = values.find(_.keyCode == keyCode)
  }
}
