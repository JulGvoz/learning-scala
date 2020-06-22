package rational

import scala.language.implicitConversions

class Rational(n: Int, d: Int) extends Ordered[Rational] {

	require(d != 0)

	private val g = this.gcd(n.abs, d.abs)

	val numer: Int = n / this.g
	val denom: Int = d / this.g


	def this(n: Int) = this(n, 1) // auxiliary constructor

    def compare(that: Rational): Int = 
        (this.numer * that.denom) - (that.numer * this.denom)

	override def toString = 
		this.numer.toString + "/" + this.denom.toString

	def + (that: Rational): Rational =
		new Rational(
			this.numer * that.denom + that.numer * this.denom,
			this.denom * that.denom
		)

	def + (i: Int): Rational =
		new Rational(
			this.numer + i * this.denom,
			this.denom
		)

	def - (that: Rational): Rational = 
		new Rational(
			this.numer * that.denom - that.numer * this.denom,
			this.denom * that.denom
		)

	def - (i: Int): Rational =
		new Rational(
			this.numer - i * this.denom,
			this.denom
		)

	def * (that: Rational): Rational = 
		new Rational(
			this.numer * that.numer,
			this.denom * that.denom
		)

	def * (i: Int): Rational =
		new Rational(
			this.numer * i,
			this.denom
		)

	def / (that: Rational): Rational = 
		new Rational(
			this.numer * that.denom,
			this.denom * that.numer
		)

	def / (i: Int): Rational = 
		new Rational(
			this.numer,
			this.denom * i
		)

	def unary_- : Rational =
		new Rational(
			-this.numer,
			this.denom
		)

	def < (i: Int): Boolean = {
		val that = new Rational(i)
		this < that
	}

	def > (i: Int): Boolean = {
		val that = new Rational(i)
		this > that
	}

	def max(that: Rational): Rational = 
		if (this < that) that else this

	def max(i: Int): Rational = {
		val that = new Rational(i)
		this max that
	}

	def min(that: Rational): Rational =
		if (this > that ) that else this

	def min(i: Int): Rational = {
		val that = new Rational(i)
		this min that
	}

	private def gcd(a: Int, b: Int): Int =
		if (b == 0) a else gcd(b, a % b)
}

object Rational {
  implicit def intToRational(x: Int): Rational = new Rational(x)
}