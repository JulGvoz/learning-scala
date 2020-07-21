package example

import scala.language.implicitConversions

object ExampleShow extends App {
  println((1 to 5).toList flatMap (x => 1 to x))
}