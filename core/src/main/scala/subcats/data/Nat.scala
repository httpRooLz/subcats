package subcats.data

import subcats.category.{Semicategory, Category, CategoryK}

/** Run a proof along a data constructor.
  */
trait Along[T[_], F[_]] {
  def run[A](implicit A: T[A]): T[F[A]]
}

/** Something like a natural transformation.
  */
trait Nat[->[_, _], T[_], A[_], B[_]] { ab =>
  def apply[X](implicit X: T[X]): A[X] -> B[X]

  def andThen[C[_]](bc: Nat[->, T, B, C])(implicit C: Semicategory[->]): Nat[->, T, A, C] =
    new Nat[->, T, A, C] {
      def apply[X](implicit X: T[X]): A[X] -> C[X] = C.andThen(ab.apply[X](X))(bc.apply[X](X))
    }
}

object Nat {
  def id[->[_, _], T[_], A[_]](implicit C: Category.Aux[->, T], A: T Along A): Nat[->, T, A, A] =
    new Nat[->, T, A, A] {
      override def apply[X](implicit X: T[X]): A[X] -> A[X] = C.id[A[X]](A.run[X](X))
    }

//  def category[->[_, _], T[_]](implicit C: Category.Aux[->, T]): CategoryK.Aux[λ[(A[_], B[_]) => Nat[->, T, A, B]], λ[F[_] => T Along F]] =
//    new CategoryK.Aux[λ[(A[_], B[_]) => Nat[->, T, A, B]], λ[F[_] => T Along F]] {
//      override def id[A[_]](implicit A: T Along A): Nat[->, T, A, A] = Nat.id[->, T, A](C, A)
//      override def andThen[A[_], B[_], C[_]](g: C1[A, B])(f: C1[B, C]): C1[A, C] = g.andThen(f)(C)
//      override def compose[A[_], B[_], C[_]](f: C1[B, C])(g: C1[A, B]): C1[A, C] = g.andThen(f)(C)
//    }
}
