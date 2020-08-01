package example

import org.stairwaybook.recipe._

object ExampleShow extends App {
  val db: Database =
    if (args(0) == "student")
      StudentDatabase
    else
      SimpleDatabase
  
  object browser extends Browser {
    val database: db.type = db
  }

  val apple = SimpleDatabase.foodNamed("Apple").get

  for (recipe <- browser.recipesUsing(apple))
    println(recipe)
}