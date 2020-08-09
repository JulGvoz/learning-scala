package parsing

import scala.util.parsing.combinator._

class JSON extends JavaTokenParsers {
  def value: Parser[Any] = obj | arr | stringLiteral |
    floatingPointNumber | "null" | "true" | "false"
  def obj: Parser[Any] = "{"~repsep(member, ",")~"}"
  def arr: Parser[Any] = "["~repsep(value, ",")~"]"
  def member: Parser[Any] = stringLiteral~":"~value
}

object ExampleJSON extends JSON {
  import java.io.FileReader
  def run(filename: String): ParseResult[Any] = {
    val reader = new FileReader(filename)
    parseAll(value, reader)
  }
}
/*
value	::=	obj  |  arr  |  stringLiteral  |
floatingPointNumber  |
"null"  |  "true"  |  "false".
obj	::=	"{"  [members]  "}".
arr	::=	"["  [values]  "]".
members	::=	member  \{","  member\}.
member	::=	stringLiteral  ":"  value.
values	::=	value  \{","  value\}.
*/