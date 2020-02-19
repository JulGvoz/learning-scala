import org.scalatest.funsuite.AnyFunSuite
import element.Element.elem

class ElementSuite extends AnyFunSuite {

    test("elem result should have passed width") {
        val ele = elem('x', 2, 3)
        assertResult(2) {
            ele.width
        }
    }

    test("elem should throw IllegalArgumentException when given negative argument") {
        assertThrows[IllegalArgumentException] {
            elem('x', -2, 3)
        }
    }
}