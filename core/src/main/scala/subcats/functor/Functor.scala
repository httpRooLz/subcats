package subcats.functor

import simulacrum.typeclass
import subcats.category.Category

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
  type Aux[F[_], C1_[_, _], C0_[_], D1_[_, _], D0_[_]] = Functor[F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
    type D0[A] = D0_[A]
    type D1[A, B] = D1_[A, B]
  }
}