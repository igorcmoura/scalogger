package scalogger.engine

import javafx.scene.image.Image

object Resources {

  object Sprite extends Enumeration {
    type Sprite = Value
    val FROG_IDLE = new Image("file:sprites/frog_idle.png")
    val FROG_JUMPING = new Image("file:sprites/frog_jumping.png")
  }

}
