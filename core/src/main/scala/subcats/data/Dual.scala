package subcats.data

import subcats.category.Category


final case class Dual[->[_, _], A, B](f: B -> A)
object Dual {
  def category[->[_, _]](implicit C: Category[->]): Category.Aux[Dual[->, ?, ?], C.C0] = new Category[Dual[->, ?, ?]] {
    override type C0[A] = C.C0[A]
    override def compose[A, B, C](bc: Dual[->, B, C])(ab: Dual[->, A, B]): Dual[->, A, C] = Dual(C.compose(ab.f)(bc.f))
    override def id[A](implicit A: C0[A]): Dual[->, A, A] = Dual(C.id)
  }
}