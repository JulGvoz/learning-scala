package rna

import rna._
import collection.mutable
import collection.immutable.{IndexedSeq, IndexedSeqOps}
import collection.View

final class RNA2 private (val groups: Array[Int],
  val length: Int) extends IndexedSeq[Base]
  with IndexedSeqOps[Base, IndexedSeq, RNA2] {
  
  import rna.RNA2._

  def apply(idx: Int): Base = {
    if (idx < 0 || length <= idx)
      throw new IndexOutOfBoundsException
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }

  override protected def fromSpecific(
    source: IterableOnce[Base]): RNA2 = 
    fromSeq(source.iterator.toSeq)
  
  override protected def newSpecificBuilder: mutable.Builder[Base, RNA2] = 
    iterableFactory.newBuilder[Base].mapResult(fromSeq)

  override def empty: RNA2 = fromSeq(Seq.empty)

  override def className: String = "RNA2"

  def appended(base: Base): RNA2 = 
  fromSpecific(new View.Appended(this, base))

  def appendedAll(suffix: IterableOnce[Base]): RNA2 = 
    concat(suffix)
  
  def concat(suffix: IterableOnce[Base]): RNA2 =
    fromSpecific(this.iterator ++ suffix.iterator)
  
  def flatMap(f: Base => IterableOnce[Base]): RNA2 =
    fromSpecific(new View.FlatMap(this, f))
  
  def map(f: Base => Base): RNA2 =
    fromSpecific(new View.Map(this, f))
  
  def prepended(elem: Base): RNA2 = 
    fromSpecific(new View.Prepended(elem, this))
  
  def prependedAll(prefix: IterableOnce[Base]): RNA2 =
    fromSpecific(prefix.iterator ++ this.iterator)
  
  @inline final def ++ (suffix: IterableOnce[Base]): RNA2 =
    concat(suffix)
}

object RNA2 {
  // Number of bits necessary to represent group
  private val S = 2
  // Number of groups that fit in an Int
  private val N = 32 / S
  // Bitmask to isolate a group
  private val M = (1 << S) - 1


  def fromSeq(buf: collection.Seq[Base]): RNA2 = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- 0 until buf.length)
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    new RNA2(groups, buf.length)
  }

  def apply(bases: Base*) = fromSeq(bases)
}
