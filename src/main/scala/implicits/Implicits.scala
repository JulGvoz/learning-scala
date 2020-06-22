package implicits

import javax.swing._
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import scala.language.implicitConversions

object Implicits {
  val button = new JButton
  button.addActionListener(
    new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        println("pressed")
      }
    }
  )

  val button2 = new JButton
  button2.addActionListener(
    (_: ActionEvent) => println("pressed!")
  )

  implicit def function2ActionListener(f: ActionEvent => Unit) = 
    new ActionListener{
      def actionPerformed(event: ActionEvent) = f(event)
    }
}