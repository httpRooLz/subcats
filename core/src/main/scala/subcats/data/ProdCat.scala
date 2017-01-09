package subcats.data

import cats.data.Prod
import subcats.category.Category

final case class ProdCat[->[_, _], ~>[_, _], A, B](first: A -> B, second: A ~> B)
object ProdCat {
  def category[->[_, _], C_->[_], ~>[_, _], C_~>[_]]
  (implicit C: Category.Aux[->, C_->], D: Category.Aux[~>, C_~>]): Category.Aux[ProdCat[->, ~>, ?, ?], Prod[C_->, C_~>, ?]] =
    new Category.Aux[ProdCat[->, ~>, ?, ?], Prod[C_->, C_~>, ?]] {
      override def id[A](implicit A: Prod[C_->, C_~>, A]): ProdCat[->, ~>, A, A] =
        ProdCat(C.id[A](A.first), D.id[A](A.second))
      override def compose[X, Y, Z](bc: ProdCat[->, ~>, Y, Z])(ab: ProdCat[->, ~>, X, Y]): ProdCat[->, ~>, X, Z] =
        ProdCat(
          C.compose[X, Y, Z](bc.first)(ab.first),
          D.compose[X, Y, Z](bc.second)(ab.second))
    }
}