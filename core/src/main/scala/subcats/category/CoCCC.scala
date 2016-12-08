package subcats.category

import simulacrum.typeclass

@typeclass trait CoCCC[->[_, _]] extends Category[->] {
  type CoExp[_, _]

  type Sum[_, _]
  type SumId
  def cocartesian: Cocartesian.Aux[C1, C0, Sum, SumId]

  def coapply[A, B]: B -> Sum[CoExp[A, B], A]
  def cocurry[X, Y, Z](f: C1[Z, CoExp[X, Y]]): CoExp[Y, Z] -> X
  def uncocurry[X, Y, Z](f: C1[CoExp[Y, Z], X]): Z -> CoExp[X, Y]
}
object CoCCC {
  type Aux[C1_[_, _], C0_[_], S[_, _], SI, E[_, _]] = CoCCC[C1_] {
    type C0[A] = C0_[A]

    type CoExp[A, B] = E[A, B]

    type Sum[A, B] = S[A, B]
    type SumId = SI
  }
}