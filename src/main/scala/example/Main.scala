package example

import parsing.ArithmeticParsing

object ExampleShow extends App {
  println("input: " + args(0))
  val parser = new ArithmeticParsing
  val parsed = parser.parseAll(parser.expr, args(0))
  println(parsed)
}