package subcats.category

import subcats.bifunctor.Bifunctor

trait Associative[C[_, _], F[_, _]] {
  type C0[_]
  type C1[A, B] = C[A, B]
  def C: Category.Aux[C1, C0]

  def bifunctor: Bifunctor.Aux[F, C1, C0, C1, C0, C1, C0]

  def associate[X, Y, Z]: C1[F[F[X, Y], Z], F[X, F[Y, Z]]]
  def diassociate[X, Y, Z]: C1[F[X, F[Y, Z]], F[F[X, Y], Z]]
}
object Associative {
  type Aux[C1_[_, _], C0_[_], F[_, _]] = Associative[C1_, F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
  }
}