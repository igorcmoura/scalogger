package scalogger.engine

import javafx.scene.image.Image

object Resources {

  object Sprite extends Enumeration {
    type Sprite = Value
    val FROG_IDLE = new Image("file:sprites/frog_idle.png")
    val FROG_JUMPING = new Image("file:sprites/frog_jumping.png")

    val CAR = new Image("file:sprites/car.png")
    val BULLDOZER = new Image("file:sprites/bulldozer.png")
    val SPORTCAR_YELLOW = new Image("file:sprites/sportcar_yellow.png")
    val SPORTCAR_GREY = new Image("file:sprites/sportcar_grey.png")
    val TRUCK = new Image("file:sprites/truck.png")

    val SIDEWALK = new Image("file:sprites/sidewalk.png")
    val FINAL = new Image("file:sprites/final.png")
  }

}
