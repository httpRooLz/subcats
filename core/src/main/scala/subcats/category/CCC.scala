package subcats.category

import simulacrum.typeclass

@typeclass trait CCC[->[_, _]] extends Category[->] {
  type Exp[_, _]

  type Product[_, _]
  type ProductId
  def cartesian: Cartesian.Aux[C1, C0, Product, ProductId]

  def apply[A, B]: Product[Exp[A, B], A] -> B
  def curry[X, Y, Z](f: C1[Product[X, Y], Z]): X -> Exp[Y, Z]
  def uncurry[X, Y, Z](f: C1[X, Exp[Y, Z]]): Product[X, Y] -> Z
}
object CCC {
  type Aux[C1_[_, _], C0_[_], P[_, _], PI, E[_, _]] = CCC[C1_] {
    type C0[A] = C0_[A]

    type Exp[A, B] = E[A, B]

    type Product[A, B] = P[A, B]
    type ProductId = PI
  }
}