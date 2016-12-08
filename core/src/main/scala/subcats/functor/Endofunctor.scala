package subcats.functor

import simulacrum.typeclass
import subcats.category.Category

@typeclass trait Endofunctor[F[_]] extends Functor[F] {
  type D0[A] = C0[A]
  type D1[A, B] = C1[A, B]
  def D : Category.Aux[D1, D0] = C

  def map[A, B](f: C1[A, B]): C1[F[A], F[B]]
}
object Endofunctor {
  type Aux[F[_], C1_[_, _], C0_[_]] = Functor[F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
  }
}