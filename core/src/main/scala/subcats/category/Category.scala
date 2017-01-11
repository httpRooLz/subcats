package subcats.category

import cats.Trivial
import simulacrum.typeclass

import scala.language.implicitConversions

@typeclass trait Category[->[_, _]] extends Semicategory[->] {
  type C0[A]
  def id[A](implicit A: C0[A]): A -> A
}
object Category {
  trait Aux[C1[_, _], C0_[_]] extends Category[C1] { type C0[A] = C0_[A] }
  trait AuxT[C1[_, _]] extends Aux[C1, Trivial.P1]
}

@typeclass trait CategoryK[->[_[_], _[_]]] extends SemicategoryK[->] {
  type C0[A[_]]
  def id[A[_]](implicit A: C0[A]): A -> A
}
object CategoryK {
  trait Aux[C1[_[_], _[_]], C0_[_[_]]] extends CategoryK[C1] { type C0[A[_]] = C0_[A] }
  trait AuxT[C1[_[_], _[_]]] extends Aux[C1, Trivial.PH1]
}