package subcats.instances

import cats.Trivial
import subcats.category.Category

/**
  * Created by alex on 12/7/16.
  */
trait TrivialInstances {
  implicit def category: Category.Aux[Trivial.P2, Trivial.P1] = new Category[Trivial.P2] {
    override type C0[A] = Trivial.P1[A]
    override def compose[A, B, C](bc: Trivial.P2[B, C])(ab: Trivial.P2[A, B]): Trivial.P2[A, C] =
      cats.Trivial.catsTrivialInstance
    override def id[A](implicit A: C0[A]): Trivial.P2[A, A] =
      cats.Trivial.catsTrivialInstance
  }
}
