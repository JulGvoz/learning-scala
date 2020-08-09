package parsing

import scala.util.parsing.combinator._

class ArithmeticParsing extends JavaTokenParsers {
  def expr: Parser[Any] = term~rep("+"~term | "-"~term)
  def term: Parser[Any] = factor~rep("*"~factor | "/"~factor)
  def factor: Parser[Any] = floatingPointNumber | "("~expr~")"
}

object MyParsers extends RegexParsers {
  val identifier: Parser[String] = """[a-zA-Z_]\w*""".r
}