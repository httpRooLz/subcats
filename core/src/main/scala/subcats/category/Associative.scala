package subcats.category

import subcats.bifunctor.Bifunctor

trait Associative[->[_, _], F[_, _]] {
  type C0[_]
  type C1[A, B] = A -> B
  def C: Category.Aux[C1, C0]
  def bifunctor: Bifunctor.Aux1[F, C1, C0]

  def associate[X, Y, Z]: F[F[X, Y], Z] -> F[X, F[Y, Z]]
  def diassociate[X, Y, Z]: F[X, F[Y, Z]] -> F[F[X, Y], Z]
}
object Associative {
  trait Aux[C1_[_, _], C0_[_], F[_, _]] extends Associative[C1_, F] {
    type C0[A] = C0_[A]
  }
}