package siom

import scala.collection.mutable.Map

object Bankomatas {
  val cache: Map[Int, Int] = Map();

  def costDiff(diff: Int): Int = {
    cache get diff match {
      case Some(value) => value
      case None => {
        val range = 1 to diff
        val possibleValues = range map (x => x + math.max(costDiff(x), cost(x + 1, diff)))
        possibleValues.min
      }
    }
  }

  def cost(a: Int, b: Int): Int = {
    5
  }

}