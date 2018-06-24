package scalogger.managers

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text}
import scalogger.engine.{GameEntity, Observable, Observer}
import scalogger.entities.FrogNotifier.Signals
import scalogger.entities.FrogNotifier.Signals.Signals
import scalogger.entities.{Frog, FrogNotifier}

class LifeManager(map: GameMap, frog: Frog, private var lives: Int) {
  private val observable = new Observable[Unit]
  initialize()

  private def initialize(): Unit = {
    val frogObserver = new Observer[FrogNotifier.Signals.Signals] {
      override def onNotify(signal: Signals): Unit = {
        signal match {
          case Signals.DEATH => lives -= 1
          case _ =>
        }
        if (lives < 0) {
          endGame()
        }
      }
    }
    frog.addObserver(frogObserver)
  }

  def addObserver(observer: Observer[Unit]): Unit = {
    observable.addObserver(observer)
  }

  private def endGame(): Unit = {
    val gameOver: GameEntity = new GameEntity {
      val text = new Text("GAME OVER")
      text.setFont(Font.font("Verdana", 20))
      text.setFill(Color.RED)
      text.setX((map.mapWidth * map.gridSize / 2) - (text.getLayoutBounds.getWidth / 2))
      text.setY(8.5 * map.gridSize)
      var timeLeft: Double = 3000

      override def attachToScreen(screen: Pane): Unit = screen.getChildren.add(text)

      override def detachFromScreen(screen: Pane): Unit = screen.getChildren.remove(text)

      override def update(deltaTime: Double): Unit = {
        this.timeLeft -= deltaTime
        if (timeLeft <= 0) {
          observable.notify(null)
        }
      }

      override def render(): Unit = {
        text.toFront()
      }
    }
    GameController.removeGameEntity(frog)
    GameController.addGameEntity(gameOver)
  }
}
