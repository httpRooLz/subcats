package subcats.category

import simulacrum.typeclass

@typeclass trait Distributive[->[_, _]] extends Category[->] {
  type ProductId
  type Product[_, _]
  type SumId
  type Sum[_, _]

  def cartesian: Cartesian.Aux[C1, C0, Product, ProductId]
  def cocartesian: Cocartesian.Aux[C1, C0, Sum, SumId]

  def distribute[A, B, C]: Product[A, Sum[B, C]] -> Sum[Product[A, B], Product[A, C]]
}
object Distributive {
  type Aux[C1_[_, _], C0_[_], P[_, _], PI, S[_, _], SI] = Distributive[C1_] {
    type C0[A] = C0_[A]

    type ProductId = PI
    type Product[A, B] = P[A, B]
    type SumId = SI
    type Sum[A, B] = S[A, B]
  }
}