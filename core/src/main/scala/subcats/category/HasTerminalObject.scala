package subcats.category

import simulacrum.typeclass

@typeclass trait HasTerminalObject[->[_, _]] extends Category[->] {
  type Terminal
  def terminal : C0[Terminal]

  def terminate[A](implicit A: C0[A]): A -> Terminal
}
object HasTerminalObject {
  trait Aux[C1[_, _], C0[_], T] extends HasTerminalObject[C1] with Category.Aux[C1, C0] {
    type Terminal = T
  }
}