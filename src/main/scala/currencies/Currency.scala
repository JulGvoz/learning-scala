package currencies
/*
abstract class Currency {
    val amount: Long
    def designation: String
    override def toString(): String = amount.toString + " " + designation
    def + (that: Currency): Currency;
    def * (x: Double): Currency;
}
*/



abstract class CurrencyZone {
    type Currency <: AbstractCurrency
    def make(x: Long): Currency

    val CurrencyUnit: Currency

    
    
    object Converter {
      val exchangeRate: Map[String, Map[String, Double]] = ???
    }

    abstract class AbstractCurrency {
        val amount: Long
        def designation: String
        override def toString(): String = 
          (amount.toDouble / CurrencyUnit.amount.toDouble) formatted ("%." + decimals(CurrencyUnit.amount) + "f") + " " + designation
        def + (that: Currency): Currency = 
          make(this.amount + that.amount)
        def * (x: Double): Currency = 
          make((this.amount * x).toLong)

        private def decimals(n: Long): Int = 
          if (n == 1) 0
          else 1 + decimals(n / 10)
        
        def from(other: CurrencyZone#AbstractCurrency): Currency =
          make(
            math.round(
              other.amount.toDouble * Converter.exchangeRate
                (other.designation)(this.designation)
            )
          )
    }
}

object US extends CurrencyZone {
  abstract class Dollar extends AbstractCurrency {
    def designation = "USD"
  }

  type Currency = Dollar
  def make(cents: Long) = new Dollar {
    val amount = cents
  }

  val Cent = make(1)
  val Dollar = make(100)
  val CurrencyUnit = Dollar
}