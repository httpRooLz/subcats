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
  trait Aux[C1[_, _], C0[_], P[_, _], PI, S[_, _], SI] extends Distributive[C1] with Category.Aux[C1, C0] {
    type ProductId = PI
    type Product[A, B] = P[A, B]
    type SumId = SI
    type Sum[A, B] = S[A, B]
  }
}