package scalogger.managers

import javafx.scene.image.Image

object Resources {

  object Sprite extends Enumeration {
    type Sprite = Value
    val FROG_IDLE = new Image("file:sprites/frog_idle.png")
    val FROG_JUMPING = new Image("file:sprites/frog_jumping.png")

    val FROG_WIN = new Image("file:sprites/frog_win.png")
    val FROG_DEATH_1 = new Image("file:sprites/frog_death_1.png")
    val FROG_DEATH_2 = new Image("file:sprites/frog_death_2.png")
    val FROG_DEATH_3 = new Image("file:sprites/frog_death_3.png")
    val FROG_DROWNING_1 = new Image("file:sprites/frog_drowning_1.png")
    val FROG_DROWNING_2 = new Image("file:sprites/frog_drowning_2.png")
    val FROG_DROWNING_3 = new Image("file:sprites/frog_drowning_3.png")
    val SKULL = new Image("file:sprites/skull.png")

    val CAR = new Image("file:sprites/car.png")
    val BULLDOZER = new Image("file:sprites/bulldozer.png")
    val SPORTCAR_YELLOW = new Image("file:sprites/sportcar_yellow.png")
    val SPORTCAR_GREY = new Image("file:sprites/sportcar_grey.png")
    val TRUCK = new Image("file:sprites/truck.png")

    val LOG = new Image("file:sprites/log.png")
    val TURTLE_1 = new Image("file:sprites/turtle_1.png")
    val TURTLE_2 = new Image("file:sprites/turtle_2.png")
    val TURTLE_3 = new Image("file:sprites/turtle_3.png")
    val TURTLE_DIVE_1 = new Image("file:sprites/turtle_dive_1.png")
    val TURTLE_DIVE_2 = new Image("file:sprites/turtle_dive_2.png")

    val SIDEWALK = new Image("file:sprites/sidewalk.png")
    val FINAL = new Image("file:sprites/final.png")

    val VOID = new Image("file:sprites/void.png")
  }

}
