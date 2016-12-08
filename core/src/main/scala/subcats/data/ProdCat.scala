package subcats.data

import cats.data.Prod
import subcats.category.Category

final case class ProdCat[->[_, _], ~>[_, _], A, B](first: A -> B, second: A ~> B)
object ProdCat {
  def category[C[_, _], D[_, _]]
  (implicit C: Category[C], D: Category[D]): Category.Aux[ProdCat[C, D, ?, ?], Prod[C.C0, D.C0, ?]] =
    new Category[ProdCat[C, D, ?, ?]] {
      override type C0[A] = Prod[C.C0, D.C0, A]

      override def id[A](implicit A: C0[A]): ProdCat[C, D, A, A] =
        ProdCat(C.id[A](A.first), D.id[A](A.second))
      override def compose[X, Y, Z](bc: ProdCat[C, D, Y, Z])(ab: ProdCat[C, D, X, Y]): ProdCat[C, D, X, Z] =
        ProdCat(
          C.compose[X, Y, Z](bc.first)(ab.first),
          D.compose[X, Y, Z](bc.second)(ab.second))
    }
}