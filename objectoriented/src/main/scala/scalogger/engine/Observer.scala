package scalogger.engine

trait Observer[T] {
  def onNotify(signal: T)
}
