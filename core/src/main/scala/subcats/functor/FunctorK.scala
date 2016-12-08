package subcats.functor

import simulacrum.typeclass
import subcats.category.CategoryK

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
  type Aux[F[_[_], _], C1_[_[_], _[_]], C0_[_[_]], D1_[_[_], _[_]], D0_[_[_]]] = FunctorK[F] {
    type C0[A[_]] = C0_[A]
    type C1[A[_], B[_]] = C1_[A, B]
    type D0[A[_]] = D0_[A]
    type D1[A[_], B[_]] = D1_[A, B]
  }
}