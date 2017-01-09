package subcats.category

import cats.Trivial
import simulacrum.typeclass
import subcats.functor.Endofunctor

import scala.language.implicitConversions

@typeclass trait Category[->[_, _]] extends Semicategory[->] {
  type C0[A]
  type C1[A, B] = A -> B

  def id[A](implicit A: C0[A]): A -> A

  def lift[F[_], A, B](f: A -> B)(implicit F: Endofunctor.Aux[F, C1, C0]): F[A] -> F[B] =
    F.map(f)
}
object Category {
  trait Aux[C1[_, _], C0_[_]] extends Category[C1] { type C0[A] = C0_[A] }
  trait AuxT[C1[_, _]] extends Aux[C1, Trivial.P1]
}