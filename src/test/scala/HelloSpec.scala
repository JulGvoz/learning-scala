import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.diagrams.Diagrams
import element._
import element.Element.elem

class HelloSpec extends AnyFunSuite with Diagrams {
  test("Hello should start with H") {
    // Hello, as opposed to hello
    assert("Hello".startsWith("H"))
  }

  test("Empty Element height should be 0") {
    val elem1 = elem(Array[String]());
    assert(elem1.height == 0)
  }

  test("Empty Element width should be 0") {
    val elem2 = elem(Array[String]());
    assert(elem2.width == 0)
  }
}