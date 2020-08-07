import scala.concurrent.Await
import scala.concurrent.duration._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.concurrent.Future


import org.scalatest.concurrent.ScalaFutures._
import org.scalatest.funspec.AsyncFunSpec

class FutureSpec extends AnyFlatSpec with Matchers {
  import scala.concurrent.ExecutionContext.Implicits.global
  "Future" should "return 42 after 1 second" in {
    val fut = Future {
      Thread.sleep(1000)
      21 + 21
    }
    val x = Await.result(fut, 2.seconds)

    x should be (42)
  }

  "Another Future" should "also return 42 after 100 miliseconds" in {
    val fut2 = Future {
      Thread.sleep(100)
      21 + 21
    }
    fut2.futureValue should be (42)
  }
}

class AddSpec extends AsyncFunSpec {
  def addSoon(addends: Int*): Future[Int] = 
    Future(addends.sum)
  
  describe("addSoon") {
    it("will eventually compute a sum of passed Ints") {
      val futureSum: Future[Int] = addSoon(1, 1, 2, 3, 5, 8, 13, 21)

      futureSum map {
        sum => assert(sum == 54)
      }
    }
  }
}