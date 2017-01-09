package subcats.instances

import cats.Trivial
import cats._
import cats.arrow.FunctionK
import subcats.category.CategoryK

trait FunctionKInstances {
//  implicit val category: CategoryK.Aux[FunctionK, Trivial.PH1] = new CategoryK.Aux[FunctionK, Trivial.PH1] {
//    override def id[A[_]](implicit A: Trivial.PH1[A]): A ~> A = FunctionK.id[A]
//    override def compose[A[_], B[_], C[_]](f: B ~> C)(g: A ~> B): A ~> C = f.compose(g)
//  }
}
