package subcats.bifunctor

import simulacrum.typeclass
import subcats.category.Category

@typeclass trait Bifunctor[P[_, _]] {
  type L0[_]
  type L1[_, _]
  def L : Category.Aux[L1, L0]

  type R0[_]
  type R1[_, _]
  def R : Category.Aux[R1, R0]

  type C0[_]
  type C1[_, _]
  def C : Category.Aux[C1, C0]

  def bimap[LX, LY, RX, RY](left: L1[LX, LY], right: R1[RX, RY]): C1[P[LX, RX], P[LY, RY]]
}
object Bifunctor {
  type Aux[P[_, _], L1_[_, _], L0_[_], R1_[_, _], R0_[_], C1_[_, _], C0_[_]] = Bifunctor[P] {
    type L0[A] = L0_[A]
    type L1[A, B] = L1_[A, B]

    type R0[A] = R0_[A]
    type R1[A, B] = R1_[A, B]

    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
  }
}