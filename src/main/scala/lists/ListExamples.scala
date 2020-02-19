package lists {

}

package object lists {

  def isort(xs: List[Int]): List[Int] = xs match {
    case Nil => Nil
    case x :: xs1 => insert(x, isort(xs1))
  }
  
  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case Nil => x :: Nil
    case y :: ys => if (x <= y) x :: xs
                    else y :: insert(x, ys)
  }
  
  def append[T](xs: List[T], ys: List[T]): List[T] = xs match {
    case Nil => ys
    case x :: xs1 => x :: append(xs1, ys)
  }

  def length[T](xs: List[T]): Int = xs match {
    case Nil => 0
    case x :: xs => 1 + length(xs)
  }

  def last[T](xs: List[T]): T = xs match {
    case Nil => throw new NoSuchElementException
    case x :: Nil => x
    case x :: xs1 => last(xs1)
  }

  def init[T](xs: List[T]): List[T] = xs match {
    case Nil => throw new UnsupportedOperationException
    case x :: Nil => Nil
    case x :: xs1 => x :: init(xs1)
  }

  def reverse[T](xs: List[T]): List[T] = xs match {
    case Nil => Nil
    case x :: xs1 => reverse(xs1) ::: List(x)
  }

  def take[T](xs: List[T], n: Int): List[T] = n match {
    case 0 => Nil
    case x if x > xs.length => xs
    case _ => xs.head :: take(xs.tail, n - 1)
  }

  def drop[T](xs: List[T], n: Int): List[T] = n match {
    case 0 => xs
    case x if x > xs.length => Nil
    case _ => drop(xs.tail, n - 1)
  }

  def splitAt[T](xs: List[T], n: Int): (List[T], List[T]) = (take(xs, n), drop(xs, n))

  def mergesort[T](comp: (T, T) => Boolean)(xs: List[T]): List[T] = xs match {
    case Nil => Nil
    case x :: Nil => List(x)
    case _ => {
      val halfLenfth = xs.length/2
      val left = mergesort(comp)(xs.take(halfLenfth))
      val right = mergesort(comp)(xs.drop(halfLenfth))
      merge(comp)(left, right)
    }
  }

  private def merge[T](comp: (T, T) => Boolean)(xs: List[T], ys: List[T]): List[T] = xs match {
    case Nil => ys
    case _ if ys.isEmpty => xs
    case x :: xs1 if comp(x, ys.head) => x :: merge(comp)(xs.tail, ys)
    case _ :: _ => ys.head :: merge(comp)(xs, ys.tail)
  }

  def flatten[T](xs: List[List[T]]): List[T] = xs match {
    case Nil => Nil
    case List(ys) => ys
    case xs1 :: ys => xs1 ::: flatten(ys)
  }

  def zip[A, B](xs: List[A], ys: List[B]): List[(A, B)] = xs match {
    case Nil => Nil
    case _ if ys.isEmpty => Nil
    case _ => (xs.head, ys.head) :: zip(xs.tail, ys.tail)
  }

  private def extractA[A, B](xs: List[(A, B)]): List[A] = xs match {
    case Nil => Nil
    case (a, _) :: _ => a :: extractA(xs.tail)
  }

  private def extractB[A, B](xs: List[(A, B)]): List[B] = xs match {
    case Nil => Nil
    case (_, b) :: _ => b :: extractB(xs.tail)
  }

  def unzip[A, B](xs: List[(A, B)]): (List[A], List[B]) = xs match {
    case Nil => (Nil, Nil)
    case _ => (extractA(xs), extractB(xs))
  }

  def mkString[T](xs: List[T], sep: String): String = xs match {
    case Nil => ""
    case x :: Nil => x.toString
    case x :: xs1 => x.toString + sep + mkString(xs1, sep)
  }

  def mkString[T](xs: List[T], pre: String, sep: String, post: String): String = pre + mkString(xs, sep) + post

  def map[A, B](xs: List[A], f: (A) => B): List[B] = xs match {
    case Nil => Nil
    case x :: xs1 => f(x) :: map(xs1, f)
  }
}