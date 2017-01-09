package subcats.functor

import simulacrum.typeclass

@typeclass trait Pointed[F[_]] extends Endofunctor[F] {
  def point[A](implicit A: C0[A], FA: C0[F[A]]): C1[A, F[A]]
}
object Pointed {
  trait Aux[F[_], C1[_, _], C0[_]] extends Pointed[F] with Endofunctor.Aux[F, C1, C0]
}