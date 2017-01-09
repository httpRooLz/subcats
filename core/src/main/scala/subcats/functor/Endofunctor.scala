package subcats.functor

import cats.Trivial
import simulacrum.typeclass
import subcats.category.Category

@typeclass trait Endofunctor[F[_]] extends Functor[F] {
  type D0[A] = C0[A]
  type D1[A, B] = C1[A, B]
  def D : Category.Aux[D1, D0] = C

  def map[A, B](f: C1[A, B]): C1[F[A], F[B]]
}
object Endofunctor {
  trait Aux[F[_], C1_[_, _], C0_[_]] extends Endofunctor[F] with Functor.Aux[F, C1_, C0_, C1_, C0_] {
    override type D0[A] = C0[A]
    override type D1[A, B] = C1[A, B]
  }
  trait AuxT[F[_], C1[_, _]] extends Aux[F, C1, Trivial.P1]
}