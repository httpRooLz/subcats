package subcats.functor

import simulacrum.typeclass
import subcats.category.CategoryK

@typeclass trait EndofunctorK[F[_[_], _]] extends FunctorK[F] {
  type D0[A[_]] = C0[A]
  type D1[A[_], B[_]] = C1[A, B]
  def D : CategoryK.Aux[D1, D0] = C

  def mapK[A[_], B[_]](f: C1[A, B]): C1[F[A, ?], F[B, ?]]
}
object EndofunctorK {
  type Aux[F[_[_], _], C1_[_[_], _[_]], C0_[_[_]]] = EndofunctorK[F] {
    type C0[A[_]] = C0_[A]
    type C1[A[_], B[_]] = C1_[A, B]
  }
}