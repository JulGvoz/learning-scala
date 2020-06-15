package forexpressions

case class Book(title: String, authors: String*)

object Queries {
  val books: List[Book] = 
    Book(
      "Structure and Interpretation of Computer Programs",
      "Abelson, Harold", "Sussman, Gerald J."
    ) ::
    Book(
      "Principles of Compiler Design",
      "Aho, Alfred", "Ullman, Jeffrey"
    ) ::
    Book(
      "Programming in Modula-2",
      "Wirth, Niklaus"
    ) ::
    Book(
      "Elements of ML Programming",
      "Ullman, Jeffrey"
    ) ::
    Book(
      "The Java Language Specification", 
      "Gosling, James", "Joy, Bill", "Steele, Guy", "Bracha, Gilad"
    ) ::
    Nil
  
  def removeDuplicates[A](xs: List[A]): List[A] = 
    if (xs.isEmpty) xs
    else
      xs.head :: removeDuplicates(
        xs.tail filter (_ != xs.head)
      )
}