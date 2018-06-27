package scalogger.engine

import scala.collection.mutable

class Observable[T] {
  var observers = new mutable.ListBuffer[Observer[T]]()

  def addObserver(observer: Observer[T]): Unit = {
    observers += observer
  }

  def removeObserver(observer: Observer[T]): Unit = {
    observers -= observer
  }

  def notify(signal: T): Unit = {
    for (observer <- observers) {
      observer.onNotify(signal)
    }
  }
}
