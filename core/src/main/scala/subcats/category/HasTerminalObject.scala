package subcats.category

import simulacrum.typeclass

@typeclass trait HasTerminalObject[C[_, _]] {
  type C0[_]
  type C1[A, B] = C[A, B]
  def C : Category.Aux[C1, C0]

  type Terminal
  def terminal : C0[Terminal]

  def terminate[A](implicit A: C0[A]): Terminal C A
}
object HasTerminalObject {
  type Aux[C1_[_, _], C0_[_], T] = HasTerminalObject[C1_] {
    type C0[A] = C0_[A]
    type Terminal = T
  }
}