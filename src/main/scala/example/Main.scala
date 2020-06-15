package example

import forexpressions.Queries.books
import forexpressions.Queries.removeDuplicates
object ExampleShow extends App {
  println(
    for {
      b <- books
      a <- b.authors
      if a startsWith "Gosling"
    } yield b.title
  )

  println(
    for {
      b <- books
      if (b.title indexOf "Program") >= 0
    } yield b.title
  )

  println(
    removeDuplicates(
      for {
        b1 <- books
        b2 <- books
        if b1 != b2
        a1 <- b1.authors
        a2 <- b2.authors
        if a1 == a2
      } yield a1
    )
  )
}