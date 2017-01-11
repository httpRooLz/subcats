package subcats.data

import subcats.category.Category


final case class Dual[->[_, _], A, B](f: B -> A)
object Dual {
  def category[->[_, _], T[_]](implicit C: Category.Aux[->, T]): Category.Aux[Dual[->, ?, ?], T] =
    new Category.Aux[Dual[->, ?, ?], T] {
      override def id[A](implicit A: C0[A]): Dual[->, A, A] =
        Dual(C.id[A](A))
      override def andThen[A, B, C](ab: Dual[->, A, B], bc: Dual[->, B, C]): Dual[->, A, C] =
        Dual(C.andThen(bc.f, ab.f))
    }
}