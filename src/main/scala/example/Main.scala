package example

import implicits.Implicits
import rational.Rational
object ExampleShow extends App {
  val a: Int = 15
  val r: Rational = new Rational(3, 2)

  println(a + r)
} 