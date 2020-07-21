package regex

import scala.util.matching.Regex

package object regex {
  val decimal = """(-)?(\d+)(\.\d+)?""".r

  val input = "for -1.0 to 99 by 3"


  def example(): Unit = {
    for (decimal(sign, int, dec) <- decimal findAllIn input)
      println("sign: " + sign + ", integer: " + int + ", decimal: " + dec)
  }
}