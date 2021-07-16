package learn

class AllInclusiveSet[A] extends MySet[A] {

  override def contains(element: A): Boolean = true

  override def +(elem: A): MySet[A] = this

  override def ++(anotherSet: MySet[A]): MySet[A] = this

  override def map[B](f: A => B): MySet[B] = ???

  override def flatMap[B](f: A => MySet[B]): MySet[B] = ???

  override def filter(f: A => Boolean): MySet[A] = ???

  override def -(element: A): MySet[A] = ???

  override def &(otherSet: MySet[A]): MySet[A] = filter(otherSet)

  override def --(otherSet: MySet[A]): MySet[A] = filter(!otherSet)

  override def unary_! : MySet[A] = new EmptySet[A]

  override def foreach(f: A => Unit): Unit = ???
}
