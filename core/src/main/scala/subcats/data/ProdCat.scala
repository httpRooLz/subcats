package subcats.data

import cats.data.Prod
import subcats.category.{Semicategory, Category}

final case class ProdCat[->[_, _], ~>[_, _], A, B](first: A -> B, second: A ~> B) { ab =>
  def andThen[C](bc: ProdCat[->, ~>, B, C])(implicit C: Semicategory[->], D: Semicategory[~>]): ProdCat[->, ~>, A, C] =
    ProdCat(C.andThen(ab.first, bc.first), D.andThen(ab.second, bc.second))
}

object ProdCat {
  def id[->[_, _], ->#[_], ~>[_, _], ~>#[_], A]
  (implicit C: Category.Aux[->, ->#], D: Category.Aux[~>, ~>#], AC: ->#[A], AD: ~>#[A]): ProdCat[->, ~>, A, A] =
    ProdCat(C.id[A](AC), D.id[A](AD))

  def category[->[_, _], ->#[_], ~>[_, _], ~>#[_]]
  (implicit C: Category.Aux[->, ->#], D: Category.Aux[~>, ~>#]): Category.Aux[ProdCat[->, ~>, ?, ?], Prod[->#, ~>#, ?]] =
    new Category.Aux[ProdCat[->, ~>, ?, ?], Prod[->#, ~>#, ?]] {
      override def id[A](implicit A: Prod[->#, ~>#, A]): ProdCat[->, ~>, A, A] =
        ProdCat.id[->, ->#, ~>, ~>#, A](C, D, A.first, A.second)
      override def andThen[X, Y, Z](ab: ProdCat[->, ~>, X, Y], bc: ProdCat[->, ~>, Y, Z]): ProdCat[->, ~>, X, Z] =
        ab.andThen(bc)
    }
}