package learn

import scala.annotation.tailrec

trait MySet[A] extends (A => Boolean) {

  def apply(element: A) : Boolean =
   contains(element)

  def contains(element: A) : Boolean

  def +(elem: A): MySet[A]

  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]

  def flatMap[B](f: A => MySet[B]): MySet[B]

  def filter(f: A => Boolean): MySet[A]

  def -(element: A) : MySet[A]

  def &(otherSet: MySet[A]): MySet[A] //union

  def --(element: MySet[A]) : MySet[A] //intersection

  def unary_! : MySet[A]

  def foreach(f: A => Unit): Unit

}

class EmptySet[A] extends MySet[A] {

  override def contains(element: A): Boolean = false

  override def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)

  override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  override def map[B](f: A => B): MySet[B] = new EmptySet[B]

  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

  override def filter(f: A => Boolean): MySet[A] = this

  override def foreach(f: A => Unit): Unit = ()

  override def -(element: A): MySet[A] = this

  override def &(otherSet: MySet[A]): MySet[A] = otherSet // union

  override def --(anotherSet: MySet[A]): MySet[A] = anotherSet

  override def unary_! : MySet[A] = new AllInclusiveSet[A]
}

class NonEmptySet[A] (head: A, tail: MySet[A]) extends MySet[A] {

  override def contains(element: A): Boolean =
    element == head || (tail contains element)

  override def +(elem: A): MySet[A] =
    if(contains(elem)) this
    else new NonEmptySet[A](elem, this)

  /**
   * [1,2,3] ++ [4,5]
   * [2,3] ++ [4,5] + 1
   * [3] ++ [4,5] + 1 + 2
   * [] ++ [4,5] + 1 + 2 + 3
   * [] ++ [4,5] + 1 + 2 + 3
   */
  override def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head


  override def map[B](f: A => B): MySet[B] =
    (tail map f) + f(head)

  override def flatMap[B](f: A => MySet[B]): MySet[B] =
    (tail flatMap f) ++ f(head)

  /**
   * cond x => x >= 2
   * [1, 2, 3]
   * [2, 3]
   * [2, [3]]
   */
  override def filter(f: A => Boolean): MySet[A] = {
    val filtered = tail filter f
    if (f(head)) filtered + head
    else filtered
  }

  override def foreach(f: A => Unit): Unit = {
    tail foreach f
    f(head)
  }

  override def -(element: A): MySet[A] =
    if(element == head) tail
    else tail - element + head

  override def &(otherSet: MySet[A]): MySet[A] =
    filter(otherSet)

  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  override def unary_! : MySet[A] = ???
}



object MySet {

  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
      if(valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values.toSeq, new EmptySet[A])
  }
}

object run extends App {

  var mySet = MySet(1, 2, 3, 4, 5)

  //mySet = mySet map (x => x*2) // double

  //mySet = mySet flatMap (x => MySet(x * 2))

  mySet = mySet ++ MySet(6, 7, 8) + 1

  //println(mySet contains 9)

  mySet foreach println
}
