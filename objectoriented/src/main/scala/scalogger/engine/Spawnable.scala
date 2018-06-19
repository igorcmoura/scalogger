package scalogger.engine

trait Spawnable[T] {
  def spawn(): T
}
