package subcats.category

import simulacrum.typeclass

@typeclass trait HasInitialObject[C[_, _]] extends Category[C] {
  type Initial
  def initial : C0[Initial]

  def initiate[A](implicit A: C0[A]): Initial C A
}
object HasInitialObject {
  trait Aux[C1[_, _], C0[_], I] extends HasInitialObject[C1] with Category.Aux[C1, C0] {
    type Initial = I
  }
}