package subcats.category

import simulacrum.typeclass

@typeclass trait HasInitialObject[C[_, _]] {
  type C0[_]
  type C1[A, B] = C[A, B]
  def C : Category.Aux[C1, C0]

  type Initial
  def initial : C0[Initial]

  def initiate[A](implicit A: C0[A]): Initial C A
}
object HasInitialObject {
  type Aux[C1_[_, _], C0_[_], I] = HasInitialObject[C1_] {
    type C0[A] = C0_[A]
    type Initial = I
  }
}