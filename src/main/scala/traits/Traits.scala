package traits

class Animal

trait Philosophical {
    def philosophize(): Unit = {
        println("I consume memeory, therefore I am!")
    }
}

trait HasLegs

class Frog extends Animal with HasLegs with Philosophical {
    override def toString = "green"
    override def philosophize(): Unit = {
        println("It ain't easy being " + toString + "!")
    }
}


trait Rectangular {
    def topLeft: Point
    def bottomRight: Point

    def left = topLeft.x
    def right = bottomRight.x
    def width = right - left

    def top = topLeft.y
    def bottom = bottomRight.y
    def height = bottom - top
}

class Point(val x: Int, val y: Int)

class Rectangle(val topLeft: Point, val bottomRight: Point) extends Rectangular {

}

abstract class Component extends Rectangular {
}

abstract class IntQueue {
    def get(): Int
    def put(x: Int): Unit
}

import scala.collection.mutable.ArrayBuffer

class BasicIntQueue extends IntQueue {
    private val buf = new ArrayBuffer[Int]
    def get() = buf.remove(0)
    def put(x: Int): Unit = {buf += x}
}

trait Doubling extends IntQueue {
    abstract override def put(x: Int): Unit = {super.put(2*x)}
}

trait Incrementing extends IntQueue {
    abstract override def put(x: Int): Unit = {super.put(x+1)}
}

trait Filtering extends IntQueue {
    abstract override def put(x: Int): Unit = {
        if (x >= 0) super.put(x)
    }
}