package subcats.category

import simulacrum.typeclass

@typeclass trait Semicategory[->[_, _]] {
  def compose[A, B, C](bc: B -> C)(ab: A -> B): A -> C
  def andThen[A, B, C](ab: A -> B)(bc: B -> C): A -> C =
    compose(bc)(ab)
}
