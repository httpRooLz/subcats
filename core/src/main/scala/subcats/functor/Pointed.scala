package subcats.functor

import simulacrum.typeclass

@typeclass trait Pointed[F[_]] extends Endofunctor[F] {
  def point[A](implicit A: C0[A], FA: C0[F[A]]): C1[A, F[A]]
}
object Pointed {
  type Aux[F[_], C1_[_, _], C0_[_]] = Pointed[F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
  }
}