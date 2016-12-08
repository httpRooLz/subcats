package subcats.comonad

import simulacrum.typeclass
import subcats.category.Category
import subcats.functor.Functor

@typeclass trait Comonad[F[_]] {
  type C0[_]
  type C1[_, _]
  def C : Category.Aux[C1, C0]

  type D0[_]
  type D1[_, _]
  def D : Category.Aux[D1, D0]

  type J[_]
  def J : Functor.Aux[J, C1, C0, D1, D0]

  def copure[A]: D1[F[A], J[A]]
  def cobind[A, B](f: D1[F[A], J[B]]): D1[F[A], F[B]]
}
object Comonad {
  type Aux[J_[_], F[_], C1_[_, _], C0_[_], D1_[_, _], D0_[_]] = Comonad[F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
    type D0[A] = D0_[A]
    type D1[A, B] = D1_[A, B]
    type J[A] = J_[A]
  }
}