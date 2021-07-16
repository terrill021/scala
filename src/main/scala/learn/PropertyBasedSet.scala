package learn

class PropertyBasedSet[A](property: A => Boolean) extends MySet [A] {

  override def contains(element: A): Boolean = ???

  override def +(elem: A): MySet[A] = ???

  override def ++(anotherSet: MySet[A]): MySet[A] = ???

  override def map[B](f: A => B): MySet[B] = ???

  override def flatMap[B](f: A => MySet[B]): MySet[B] = ???

  override def filter(f: A => Boolean): MySet[A] = ???

  override def -(element: A): MySet[A] = ???

  override def &(otherSet: MySet[A]): MySet[A] = ???

  override def --(element: MySet[A]): MySet[A] = ???

  override def unary_! : MySet[A] = ???

  override def foreach(f: A => Unit): Unit = ???
}
