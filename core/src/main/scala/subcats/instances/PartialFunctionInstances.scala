package subcats.instances

import cats.Trivial
import subcats.category.Category

trait PartialFunctionInstances {
  implicit val category: Category.Aux[PartialFunction, Trivial.P1] = new Category[PartialFunction] {
    override type C0[A] = Trivial.P1[A]
    override def compose[A, B, C](f: PartialFunction[B, C])(g: PartialFunction[A, B]): PartialFunction[A, C] =
      new PartialFunction[A, C] {
        override def isDefinedAt(x: A): Boolean = g.isDefinedAt(x) && f.isDefinedAt(g(x))
        override def apply(v1: A): C = f(g(v1))
      }
    override def id[A](implicit A: Trivial.P1[A]): PartialFunction[A, A] =
      new PartialFunction[A, A] {
        override def isDefinedAt(x: A): Boolean = true
        override def apply(v1: A): A = v1
      }
  }
}
