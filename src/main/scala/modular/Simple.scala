package org.stairwaybook.recipe

trait SimpleFoods {
  object Pear extends Food("Pear")
  def allFoods = List(Apple, Pear)
  def allCategories = Nil
}

trait SimpleRecipes {
  this: SimpleFoods =>

  object FruitSalad extends Recipe(
    "fruit salad",
    List(Apple, Pear),
    "Mix it all together."
  )
  def allRecipes = List(FruitSalad)
}