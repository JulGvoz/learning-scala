package futures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import scala.util.Success
import scala.util.Failure
import scala.util.Try

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

    val promise = Promise[Int]()

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

  def failureRun(): Unit = {
    val success = Future (42 / 2)
    val unexpectedSuccess = success.failed
    val failure = Future {42 / 0}
    val expectedFailure = failure.failed

    val fallback = failure.fallbackTo(success)
    val failedFallback = failure.fallbackTo(Future{
      val res = 42
      require(res < 0)
      res
    })
    val recoveredFallback = failedFallback recover {
      case ex: ArithmeticException => -1
    }

    Thread.sleep(10)

    println(failure.value)
    println(expectedFailure.value)
    println(unexpectedSuccess.value)

    println("-------------------")

    println(fallback.value)
    println(failedFallback.value)
    println(recoveredFallback.value)
  }

  def transformRun(): Unit = {
    val success = Future(42 / 2)
    val failure = Future(42 / 0)

    val first = success.transform(
      res => res * -1,
      ex => new Exception("see cause", ex)
    )

    val second = failure.transform(
      res => res * -1,
      ex => new Exception("see cause", ex)
    )

    Thread.sleep(1000)

    println(first.value)
    println(second.value)
  }

  def combiningRun(): Unit = {
    val success = Future(42 / 2)
    val failure = Future(42 / 0)

    val recovered = failure recover {
      case ex: ArithmeticException => -1
    }

    val fallback = failure fallbackTo success

    val zippedSuccess = success zip recovered
    val zippedFailure = failure zip success

    // --------------

    val fortyTwo = Future(21 + 21)
    val fortySix = Future(23 + 23)
    val futureNums = fortyTwo :: fortySix :: Nil

    val folded = Future.foldLeft(futureNums)(1){
      (acc, num) => acc * num
    }
    
    val reduced = Future.reduceLeft(futureNums){
      (acc, next) => acc + next
    }

    val futureList = Future.sequence(futureNums)
    
    val traversed = Future.traverse(List(1, 1, 2, 3, 5, 8, 13, 21)){
      (i) => Future(i * 2)
    }

    Thread.sleep(1000)

    println(zippedSuccess.value)
    println(zippedFailure.value)
    println(folded.value)
    println(reduced.value)
    println(futureList.value)
    println(traversed.value)
  }

  def sideEffectRun(): Unit = {
    val success = Future(42 / 2)
    val failure = Future(42 / 0)

    success.foreach(println _)
    failure.foreach(println _)

    for (res <- failure) println(res)
    for (res <- success) println(res)

    def evalTry[T](t: Try[T]): Unit = t match {
      case Success(value) => println(value)
      case Failure(exception) => println("Failed :/")
    }

    success onComplete evalTry
    failure onComplete evalTry

    val newFuture = success andThen {
      case Success(res) => println(res * 2)
      case Failure(ex) => println(ex)
    }
  }

  def otherRun(): Unit = {
    val success = Future(42 / 2)
    val failure = Future(42 / 0)

    val futNum = Future(21 + 21)
    val futStr = Future("ans" + "wer")

    val fut = futNum.zipWith (futStr) {
      (num, str) => s"$num is the $str"
    }

    Thread.sleep(1000)

    println(fut.value)
  }
}