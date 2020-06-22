package rna

import rna._
import collection.mutable
import collection.immutable.{IndexedSeq, IndexedSeqOps}

final class RNA1 private (val groups: Array[Int],
  val length: Int) extends IndexedSeq[Base]
  with IndexedSeqOps[Base, IndexedSeq, RNA1] {
  
  import rna.RNA1._

  def apply(idx: Int): Base = {
    if (idx < 0 || length <= idx)
      throw new IndexOutOfBoundsException
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }

  override protected def fromSpecific(
    source: IterableOnce[Base]): RNA1 = 
    fromSeq(source.iterator.toSeq)
  
  override protected def newSpecificBuilder: mutable.Builder[Base, RNA1] = 
    iterableFactory.newBuilder[Base].mapResult(fromSeq)

  override def empty: RNA1 = fromSeq(Seq.empty)
}

object RNA1 {
  // Number of bits necessary to represent group
  private val S = 2
  // Number of groups that fit in an Int
  private val N = 32 / S
  // Bitmask to isolate a group
  private val M = (1 << S) - 1


  def fromSeq(buf: collection.Seq[Base]): RNA1 = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- 0 until buf.length)
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    new RNA1(groups, buf.length)
  }

  def apply(bases: Base*) = fromSeq(bases)
}
