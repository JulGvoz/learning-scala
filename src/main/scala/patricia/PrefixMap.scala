package patricia

import scala.collection._

class PrefixMap[A] extends mutable.Map[String, A] 
    with mutable.MapOps[String, A, mutable.Map, PrefixMap[A]]
    with StrictOptimizedIterableOps[(String, A), mutable.Iterable, PrefixMap[A]] {
  
  import PrefixMap._

  private var suffixes: immutable.Map[Char, PrefixMap[A]] = 
    immutable.Map.empty
  
  private var value: Option[A] = None

  def get(s: String): Option[A] =
    if (s.isEmpty) value
    else suffixes get (s(0)) flatMap (_.get(s substring 1))
  
  def addOne(kv: (String, A)): this.type = {
    withPrefix(kv._1).value = Some(kv._2)
    this
  }

  def subtractOne(s: String): this.type = {
    if (s.isEmpty) {
      val prev = value
      value = None
      prev
    }
    else suffixes get (s(0)) flatMap (_.remove(s substring 1))
    this
  }

  def withPrefix(s: String): PrefixMap[A] =
    if (s.isEmpty) this
    else {
      val leading = s(0)
      suffixes get leading match {
        case None => 
          suffixes = suffixes + (leading -> PrefixMap.empty)
        case _ =>
      }
      suffixes(leading) withPrefix (s substring 1)
    }
  
  def iterator: Iterator[(String, A)] = 
    (for (v <- value.iterator) yield ("", v)) ++ 
    (for ((chr, m) <- suffixes.iterator;
          (s, v) <- m.iterator) yield (chr +: s, v))
  
  override def className: String = "PrefixMap"

  override def fromSpecific(
    source: IterableOnce[(String, A)]): PrefixMap[A] =
    PrefixMap.from(source)
  
  override def newSpecificBuilder: mutable.Builder[(String, A), PrefixMap[A]] = 
    PrefixMap.newBuilder
  
  override def empty: PrefixMap[A] = new PrefixMap
}

object PrefixMap {
  def empty[A] = new PrefixMap[A]

  def from[A](source: IterableOnce[(String, A)]): PrefixMap[A] = 
    source match {
      case pm: PrefixMap[A] => pm
      case _ => (newBuilder[A] ++= source).result()
    }
  
  def newBuilder[A]: mutable.Builder[(String, A), PrefixMap[A]] =
    new mutable.GrowableBuilder[(String, A), PrefixMap[A]](empty)
  
  def apply[A](kvs: (String, A)*): PrefixMap[A] = from(kvs)

  import scala.language.implicitConversions

  implicit def toFactory[A] (
    self: this.type): Factory[(String, A), PrefixMap[A]] =
    new Factory[(String, A), PrefixMap[A]] {
      def fromSpecific(
        source: IterableOnce[(String, A)]): PrefixMap[A] = 
        self.from(source)
      
      def newBuilder: mutable.Builder[(String, A),PrefixMap[A]] = 
        self.newBuilder
    }
}