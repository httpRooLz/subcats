package subcats.logic

import simulacrum.typeclass

/**
  * Every type has an associated logic. Most types use classical logic,
  * which corresponds to the Bool type. But types can use any logical
  * system they want. Functions, for example, use an infinite logic.
  */
@typeclass trait HasLogic[T] {
  type Logic
}
object HasLogic {
  trait Aux[T, L] extends HasLogic[T] { type Logic =  L }
  trait Classical[T] extends Aux[T, Boolean]

  def mkBoolLogic[T]: Classical[T] = new Classical[T] { }
  implicit val boolLogic: Classical[Boolean] = mkBoolLogic[Boolean]
  implicit val charLogic: Classical[Char] = mkBoolLogic[Char]
  implicit val intLogic: Classical[Int] = mkBoolLogic[Int]

  implicit val unitLogic: Aux[Unit, Unit] =
    new Aux[Unit, Unit] { }
  implicit def funcLogic[A, B, L](implicit B: Aux[B, L]): Aux[A => B, A => L] =
    new Aux[A => B, A => L] { }
}

@typeclass trait Eq_[T] extends HasLogic[T] {
  type Logic
  def eqv(a: T, b: T): Logic
}
object Eq_ {
  trait Aux[T, L] extends Eq_[T] with HasLogic.Aux[T, L]
  trait Complemented[T, L] extends Aux[T, L] {
    def notEqv(a: T, b: T): Logic
  }
  trait Classical[T] extends Complemented[T, Boolean] with HasLogic.Classical[T] {
    def notEqv(a: T, b: T): Logic = !eqv(a, b)
  }

  def mkUniversalBoolEq[T](implicit L: HasLogic.Aux[T, Boolean]): Classical[T] =
    new Classical[T] { def eqv(a: T, b: T): Boolean = a == b }
  implicit val boolEq: Eq_.Aux[Boolean, Boolean] = mkUniversalBoolEq[Boolean]
  implicit val charEq: Eq_.Aux[Char, Boolean] = mkUniversalBoolEq[Char]
  implicit val intEq: Eq_.Aux[Int, Boolean] = mkUniversalBoolEq[Int]

  implicit val unitEq: Eq_.Aux[Unit, Unit] =
    new Eq_.Aux[Unit, Unit] { def eqv(a: Unit, b: Unit): Unit = () }
  implicit def funcEq[A, B, L](implicit B: Eq_.Aux[B, L]): Eq_.Aux[A => B, A => L] =
    new Eq_.Aux[A => B, A => L] { def eqv(f: A => B, g: A => B): Logic = a => B.eqv(f(a), g(a)) }
}

//@typeclass trait POrd_[T] extends Eq_[T] {
//  def inf(a: T, b: T): T
//  def lteq(a: T, b: T): Logic
//}
//object POrd_ {
//  trait Aux[T, L] extends POrd_[T] with Eq_.Aux[T, L]
//  trait Complemented[T, L] extends Aux[T, L] {
//    def lt(a: T, b: T): Logic = eqv(inf(a, b), a)
//  }
//  trait Classical[T] extends Complemented[T, Boolean] with HasLogic.Classical[T] {
//    def notEqv(a: T, b: T): Logic = !eqv(a, b)
//  }
//}