package subcats.category

import simulacrum.typeclass

@typeclass trait CategoryK[Cat[_[_], _[_]]] {
  type C0[A[_]]
  type C1[A[_], B[_]] = Cat[A, B]

  def id[A[_]](implicit A: C0[A]): C1[A, A]
  def compose[A[_], B[_], C[_]](f: C1[B, C])(g: C1[A, B]): C1[A, C]
}
object CategoryK {
  trait Aux[C1[_[_], _[_]], C0_[_[_]]] extends CategoryK[C1] { type C0[A[_]] = C0_[A] }
}