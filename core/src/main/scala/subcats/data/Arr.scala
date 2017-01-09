package subcats.data

import subcats.category.Category

/** For C any category, its arrow category Arr(C) is the category such that:
  *   an object a of Arr(C) is a morphism a : a0 → a1 of C.
  *   a morphism f : a → b of Arr(C) is a commutative square in C:
  *       a0 →(f0) b0
  *       ↓(a)     ↓(b)
  *       a1 →(f1) b1
  */
sealed abstract class Arr[->[_, _], T[_], A, B] protected[this] () {
  def andThen[C](bc: Arr[->, T, B, C])(implicit C: Category.Aux[->, T]): Arr[->, T, A, C]
}
object Arr {
  final case class Only[->[_, _], T[_], A1, A2, B1, B2](first: A1 -> B1, second: A2 -> B2)
    extends Arr[->, T, A1 -> A2, B1 -> B2] { ab =>

    def andThen[C](bc: Arr[->, T, B1 -> B2, C])(implicit C: Category.Aux[->, T]): Arr[->, T, A1 -> A2, C] =
      bc match { case bc: Only[->, T, B1, B2, c1, c2] =>
        Only[->, T, A1, A2, c1, c2](
          C.compose(bc.first)(ab.first),
          C.compose(bc.second)(ab.second))
      }
  }

  sealed abstract class Valid[->[_, _], T[_], A] private[Arr] () {
    def id: Arr[->, T, A, A]
  }
  implicit def validArrowType[->[_, _], T[_], A1, A2](implicit C: Category.Aux[->, T], A1: T[A1], A2: T[A2]): Valid[->, T, A1 -> A2] =
    new Valid[->, T, A1 -> A2] {
      def id: Arr[->, T, A1 -> A2, A1 -> A2] = Only[->, T, A1, A2, A1, A2](C.id[A1], C.id[A2])
    }

  def category[->[_,_], T[_]](implicit C: Category.Aux[->, T]): Category.Aux[Arr[->, T, ?, ?], Arr.Valid[->, T, ?]] =
    new Category.Aux[Arr[->, T, ?, ?], Arr.Valid[->, T, ?]] {
      override def id[A](implicit A: Valid[->, T, A]): Arr[->, T, A, A] = A.id
      override def compose[A, B, C](bc: Arr[->, T, B, C])(ab: Arr[->, T, A, B]): Arr[->, T, A, C] =
        ab.andThen(bc)
    }
}