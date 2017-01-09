package subcats.category

import cats.Trivial

trait Concrete[->[_, _]] extends Category[->] {
  def concretize[A, B](f: A -> B): (A, C0[A]) => (B, C0[B])

  def concretize1[A, B](a: A)(f: A -> B)(implicit A: C0[A]): B =
    concretize[A, B](f)(a, A)._1
}
object Concrete {
  trait Aux[C1[_, _], C0[_]] extends Concrete[C1] with Category.Aux[C1, C0]
  trait AuxT[C1[_, _]] extends Aux[C1, Trivial.P1]
}