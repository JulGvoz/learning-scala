package example

import rational.Rational
import scala.Predef._
object ExampleShow extends App {
  def maxListUpBound[T <% Ordered[T]](elements: List[T]): T = 
    elements match {
      case List() => 
        throw new IllegalArgumentException("empty list")
      case List(x) => x
      case x :: rest =>
        val maxRest = maxListUpBound(rest)
        if (x > maxRest) x
        else maxRest
    }
  
  val rationalList = new Rational(3, 2) :: new Rational(5/3) :: new Rational (7, 4) :: Nil
  val intList = 3 :: 2 :: 5 :: 9 :: 4 :: Nil

  println(maxListUpBound(rationalList))
  println(maxListUpBound(intList))
}