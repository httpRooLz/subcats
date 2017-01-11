package subcats.category

import simulacrum.typeclass

@typeclass trait Semicategory[->[_, _]] {
  type C1[A, B] = ->[A, B]
  def andThen[A, B, C](ab: A -> B, bc: B -> C): A -> C
}

@typeclass trait SemicategoryK[->[_[_], _[_]]] {
  type C1[A[_], B[_]] = ->[A, B]
  def andThen[A[_], B[_], C[_]](ab: A -> B, bc: B -> C): A -> C
}
