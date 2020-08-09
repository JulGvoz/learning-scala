package swing

import scala.swing._
import scala.swing.event._
import scala.math._

object CelsiusFahrenheit extends SimpleSwingApplication {

  def celsiusToFahrenheit(cel: Double): Double = 
    cel * 1.8 + 32

  def fahrenheitToCelsius(fah: Double): Double = 
    (fah - 32)/1.8

  def top = new MainFrame {
    var celsiusValue = -40.0
    var fahrenheitValue = -40.0

    object celsius extends TextField {
      text = "-40"
      columns = 5
    }
    object fahrenheit extends TextField {
      text = "-40"
      columns = 5
    }

    contents = new FlowPanel {
      contents += celsius
      contents += new Label(" Celsius = ")
      contents += fahrenheit
      contents += new Label(" Fahrenheit")

      border = Swing.EmptyBorder(15, 10, 10, 10)
    }

    listenTo(celsius, fahrenheit)

    reactions += {
      case EditDone(`celsius`) => 
        celsiusValue = celsius.text.toDouble
        fahrenheitValue = celsiusToFahrenheit(celsiusValue)
        fahrenheit.text = fahrenheitValue.toInt.toString
      case EditDone(`fahrenheit`) =>
        fahrenheitValue = fahrenheit.text.toDouble
        celsiusValue = fahrenheitToCelsius(fahrenheitValue)
        celsius.text = celsiusValue.toInt.toString
    }
  }
}