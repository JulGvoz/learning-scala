package example
object ExampleShow extends App {
  case class PreferredPrompt(val preference: String)
  case class PreferredDrink(val preference: String)

  object Greeter {
    def greet(name: String)(implicit prompt: PreferredPrompt, drink: PreferredDrink): Unit = {
      println("Welcome "+ name +". The system is ready.")
      println("But while you wrok, why not enjoy a cup of "+ drink.preference +"?")
      println(prompt.preference)
    }
  }

  object JoesPrefs {
    implicit val prompt = new PreferredPrompt("Yes, master> ")
    implicit val drink = new PreferredDrink("tea")
  }

  val bobsPrompt = new PreferredPrompt("relax> ")
  val bobsDrink = new PreferredDrink("coffee")

  Greeter.greet("Bob")(bobsPrompt, bobsDrink)

  import JoesPrefs._

  Greeter.greet("Joe")
} 