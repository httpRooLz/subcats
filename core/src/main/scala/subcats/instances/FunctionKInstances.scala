package subcats.instances

import cats.Trivial
import cats._
import cats.arrow.FunctionK
import subcats.category.CategoryK

trait FunctionKInstances {
  implicit val category: CategoryK.Aux[FunctionK, Trivial.PH1] = new CategoryK[FunctionK] {
    override type C0[A[_]] = Trivial.PH1[A]
    override def compose[A[_], B[_], C[_]](f: B ~> C)(g: A ~> B): A ~> C = f.compose(g)
    override def id[A[_]](implicit A: Trivial.PH1[A]): A ~> A = FunctionK.id[A]
  }
}
