package example

import parsing.ExampleJSON

object ExampleShow extends App {
  println(ExampleJSON.run(args(0)))
}