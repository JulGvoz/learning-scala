package futures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FutureExample {
  def run(x: Int): Unit = {
    val fut = Future {
      Thread.sleep(1000)
      21 / x
    }

    while (!fut.isCompleted) {
      println("not completed yet")
      Thread.sleep(240)
    }

    println(fut.value)
  }
}