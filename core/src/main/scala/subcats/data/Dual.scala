package subcats.data

import subcats.category.Category


final case class Dual[->[_, _], A, B](f: B -> A)
object Dual {
  def category[->[_, _], C0[_]](implicit C: Category.Aux[->, C0]): Category.Aux[Dual[->, ?, ?], C0] =
    new Category.Aux[Dual[->, ?, ?], C0] {
      override def id[A](implicit A: C0[A]): Dual[->, A, A] =
        Dual(C.id[A](A))
      override def compose[A, B, C](bc: Dual[->, B, C])(ab: Dual[->, A, B]): Dual[->, A, C] =
        Dual(C.compose(ab.f)(bc.f))
    }
}