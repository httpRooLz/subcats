package subcats.data

import cats.data.Prod
import cats.~>
import subcats.category.Category

final case class Tagged[->[_, _], T[_], A, B](run: A -> B, evidence: Evidence[T, A, B])
object Tagged {
  def taggedFunctionCategory[T[_]]: Category.Aux[Tagged[Function1, T, ?, ?], T] =
    new Category.Aux[Tagged[Function1, T, ?, ?], T] {
      override def id[A](implicit A: C0[A]): C1[A, A] =
        Tagged(identity[A], Evidence.id[T, A](A))
      override def compose[A, B, C](bc: Tagged[Function1, T, B, C])(ab: Tagged[Function1, T, A, B]): Tagged[Function1, T, A, C] =
        Tagged(bc.run compose ab.run, bc.evidence compose ab.evidence)
    }

  def category[T[_], ->[_, _], T0[_]](C: Category.Aux[->, T0]): Category.Aux[Tagged[->, T, ?, ?], Prod[T, T0, ?]] =
    new Category.Aux[Tagged[->, T, ?, ?], Prod[T, T0, ?]] {
      override def id[A](implicit A: C0[A]): C1[A, A] =
        Tagged(C.id[A](A.second), Evidence.id(A.first))
      override def compose[A, B, C](bc: Tagged[->, T, B, C])(ab: Tagged[->, T, A, B]): Tagged[->, T, A, C] =
        Tagged(C.compose[A, B, C](bc.run)(ab.run), bc.evidence compose ab.evidence)
    }
}