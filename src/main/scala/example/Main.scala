package example

import queue._
import types._

object ExampleShow extends App {
    class Inty(val x: Int)
    class BetterInty(override val x: Int) extends Inty(x) {
        val double = x + x
    }

    class Stringy(val s: String)
    class BetterStringy(override val s: String) extends Stringy(s) {
        val double = s + s
    }

    def intyToStringy(x: Inty): Stringy =
        new BetterStringy(x.x.toString)
    
    val btrStr: Stringy = intyToStringy(new BetterInty(5))

    val str: String = btrStr.s

    println(str + " eh")
} 