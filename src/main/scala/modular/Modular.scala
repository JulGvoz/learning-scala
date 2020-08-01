package org.stairwaybook.recipe

import _root_.org.stairwaybook.recipe.org.stairwaybook.recipe.FoodCategories

abstract class Food(val name: String) {
  override def toString = name
}

class Recipe(
  val name: String,
  val ingredients: List[Food],
  val instructions: String
) {
  override def toString = name
}

abstract class Browser {
  val database: Database

  def recipesUsing(food: Food) = 
    database.allRecipes.filter(recipe =>
      recipe.ingredients.contains(food))
  
  def displayCategory(category: database.FoodCategory) = {
    println(category)
  }
}

abstract class Database extends FoodCategories {
  def allFoods: List[Food]
  def allRecipes: List[Recipe]

  def foodNamed(name: String) = 
    allFoods.find(_.name == name)
}

object Apple extends Food("Apple")
object Orange extends Food("Orange")
object Cream extends Food("Cream")
object Sugar extends Food("Sugar")

object FruitSalad extends Recipe(
  "fruit salad",
  List(Apple, Orange, Cream, Sugar),
  "Stir it all together."
)

object SimpleDatabase extends Database {
  def allFoods = List(Apple, Orange, Cream, Sugar)

  private var categories = List(
    FoodCategory("fruits", List(Apple, Orange)),
    FoodCategory("misc", List(Cream, Sugar))
  )
  
  def allRecipes: List[Recipe] = List(FruitSalad)

  def allCategories = categories
}

object SimpleBrowser extends Browser {
  val database: Database = SimpleDatabase
}

object StudentDatabase extends Database {
  object FrozenFood extends Food("FrozenFood")

  object HeatItUp extends Recipe(
    "heat it up",
    List(FrozenFood),
    "Microwave the 'food' for 10 minutes"
  )

  def allFoods: List[Food] = List(FrozenFood)
  def allRecipes: List[Recipe] = List(HeatItUp)
  def allCategories: List[FoodCategory] = List(
    FoodCategory("edible", List(FrozenFood))
  )

}

object StudentBrowser extends Browser {
  val database: Database = StudentDatabase
}