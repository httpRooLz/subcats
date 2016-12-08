package subcats.category

import simulacrum.typeclass

import scala.language.implicitConversions

@typeclass trait Category[Cat[_, _]] {
  type C0[A]
  type C1[A, B] = Cat[A, B]

  def id[A](implicit A: C0[A]): C1[A, A]
  def compose[A, B, C](bc: C1[B, C])(ab: C1[A, B]): C1[A, C]
}
object Category {
  type Aux[C1[_, _], C0_[_]] = Category[C1] { type C0[A] = C0_[A] }
}