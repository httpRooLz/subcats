package subcats.functor

import cats.Trivial
import simulacrum.typeclass
import subcats.category.{CategoryK, Category}

@typeclass trait Functor[F[_]] {
  type C0[_]
  type C1[_, _]
  def C : Category.Aux[C1, C0]

  type D0[_]
  type D1[_, _]
  def D : Category.Aux[D1, D0]

  def map[A, B](f: C1[A, B]): D1[F[A], F[B]]
}
object Functor {
  trait Aux[F[_], C1_[_, _], C0_[_], D1_[_, _], D0_[_]] extends Functor[F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
    type D0[A] = D0_[A]
    type D1[A, B] = D1_[A, B]
  }
  trait Endo[F[_], C1[_, _], C0[_]] extends Aux[F, C1, C0, C1, C0] {
    def D : Category.Aux[D1, D0] = C
  }
  trait EndoT[F[_], C1[_, _]] extends Endo[F, C1, Trivial.P1]
}

@typeclass trait FunctorK[F[_[_], _]] {
  type C0[_[_]]
  type C1[_[_], _[_]]
  def C : CategoryK.Aux[C1, C0]

  type D0[_[_]]
  type D1[_[_], _[_]]
  def D : CategoryK.Aux[D1, D0]

  def mapK[A[_], B[_]](f: C1[A, B]): D1[F[A, ?], F[B, ?]]
}
object FunctorK {
  trait Aux[F[_[_], _], C1_[_[_], _[_]], C0_[_[_]], D1_[_[_], _[_]], D0_[_[_]]] extends FunctorK[F] {
    type C0[A[_]] = C0_[A]
    type C1[A[_], B[_]] = C1_[A, B]
    type D0[A[_]] = D0_[A]
    type D1[A[_], B[_]] = D1_[A, B]
  }
  trait Endo[F[_[_], _], C1[_[_], _[_]], C0[_[_]]] extends Aux[F, C1, C0, C1, C0] {
    def D : CategoryK.Aux[D1, D0] = C
  }
  trait EndoT[F[_[_], _], C1[_[_], _[_]]] extends Endo[F, C1, Trivial.PH1]
}