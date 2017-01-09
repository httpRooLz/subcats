package subcats.logic

import simulacrum.typeclass

///**
//  * Every type has an associated logic.
//  * Most types use classical logic, which corresponds to the Bool type.
//  * But types can use any logical system they want.
//  * Functions, for example, use an infinite logic.
//  * You probably want your logic to be an instance of "Boolean", but this is not required.
//  *
//  * @tparam T
//  */
//@typeclass trait HasLogic[T] {
//  type Out
//}
//object HasLogic {
//  type Aux[T, L] = HasLogic[T] { type Out =  L }
//
//  def mkBoolLogic[T]: Aux[T, Boolean] = new HasLogic[T] { type Out = Boolean }
//
//  implicit val unitLogic: Aux[Unit, Unit] = new HasLogic[Unit] { type Out = Unit }
//  implicit val boolLogic: Aux[Boolean, Boolean] = mkBoolLogic[Boolean]
//  implicit val charLogic: Aux[Char, Boolean] = mkBoolLogic[Char]
//  implicit val intLogic: Aux[Int, Boolean] = mkBoolLogic[Int]
//  implicit def funcLogic[A, B, L](implicit B: Aux[B, L]): Aux[A => B, A => L]
//    = new HasLogic[A => B] { type Out = A => L }
//
//  type Classical[T] = Aux[T, Boolean]
//}
//
//@typeclass trait Eq_[A] {
//  type Logic
//  val logic: HasLogic.Aux[A, Logic]
//  def eqv(a: A, b: A): Logic
//}
//object Eq_ {
//  type Aux[T, L] = Eq_[T] { type Out = L }
//
//  def mkUniversalBoolEq[T](implicit L: HasLogic.Aux[T, Boolean]): Eq_.Aux[T, Boolean] =
//    new Eq_[T] {
//      type Out = Boolean
//      val logic: HasLogic.Aux[T, Out] = L
//      def eqv(a: T, b: T): Boolean = a == b
//    }
//
//  implicit val unitEq: Eq_.Aux[Unit, Unit] = new Eq_[Unit] {
//    type Out = Unit
//    val logic: HasLogic.Aux[Unit, Out] = HasLogic.unitLogic
//    def eqv(a: Unit, b: Unit): Unit = ()
//  }
//
//  implicit val boolEq: Eq_.Aux[Boolean, Boolean] = mkUniversalBoolEq[Boolean]
//  implicit val charEq: Eq_.Aux[Char, Boolean] = mkUniversalBoolEq[Char]
//  implicit val intEq: Eq_.Aux[Int, Boolean] = mkUniversalBoolEq[Int]
//
//  implicit def funcEq[A, B, L](implicit B: Eq_.Aux[B, L]): Eq_.Aux[A => B, A => L] =
//    new Eq_[A => B] {
//      type Out = A => L
//      val logic: HasLogic.Aux[A => B, Out] = HasLogic.funcLogic[A, B, L]
//      def eqv(f: A => B, g: A => B): Out = a => B.eqv(f(a), g(a))
//    }
//
//  type Classical[T] = Aux[T, Boolean]
//}