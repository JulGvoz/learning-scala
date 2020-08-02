package futures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise

object FutureExample {
  def simpleRun(x: Int): Unit = {
    val fut = Future {
      Thread.sleep(1000)
      21 / x
    }

    val result = fut map (x => {
      println("mapping " + x.toString)
      x + 1
    })

    while (!result.isCompleted) {
      println("not completed yet")
      Thread.sleep(240)
    }

    println(result.value)

    val fut1 = Future {
      Thread.sleep(1000)
      2 * x
    }

    val fut2 = Future {
      Thread.sleep(1000)
      7 / (x - 1)
    }

    val result2 = 
      for {
        a <- fut1
        b <- fut2
      } yield a + b

    while(!result2.isCompleted) {
      Thread.sleep(500)
      println("WAITING")
    }

    println(result2.value)
  }

  def creatingRun(): Unit = {
    val success = Future.successful{
      21 + 21
    }
    val failed = Future.failed{
      new Exception("bummer!")
    }

    import scala.util.{Success, Failure}

    val future_success = Future.fromTry(Success(21 + 21))
    val future_failed = Future.fromTry(Failure(new Exception("Ahh! Bummer!")))

    val promise = Promise[Int]

    val pro_fut = promise.future

    println(pro_fut.value)

    promise.success(42)

    println(pro_fut.value)

  }

  def filteringRun(): Unit = {
    val fut = Future{42}
    val valid = fut filter (_ > 0)
    val invalid = fut filter (_ < 0)

    println(valid.value)
    println(invalid.value)

    val valid2 = 
      for (
        res <- fut
        if res > 0
      ) yield res
    
    val invalid2 =
      for (
        res <- fut
        if res < 0
      ) yield res
    
    println(valid2.value)
    println(invalid2.value)

    val valid3 =
      fut collect {
        case res if res > 0 => res + 46
      }
    
    val invalid3 =
      fut collect {
        case res if res < 0 => res + 46
      }
    
    println(valid3.value)
    println(invalid3.value)
  }
}