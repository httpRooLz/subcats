package subcats.instances

import cats.Trivial
import subcats.category.Category.Aux
import subcats.data.{ Function1, Tagged }
import subcats.functor.Functor

import scala.reflect.ClassTag

trait ArrayInstances {
  implicit val arrayFunctor: Functor.Aux[Array, Tagged[Function1, ClassTag, ?, ?], ClassTag, Function1, Trivial.P1] =
    new Functor.Aux[Array, Tagged[Function1, ClassTag, ?, ?], ClassTag, Function1, Trivial.P1] {
      override def C: Aux[C1, C0] = Tagged.taggedFunctionCategory
      override def D: Aux[D1, D0] = Function1.category
      override def map[A, B](f: Tagged[Function1, ClassTag, A, B]): Array[A] => Array[B] =
        a => a.map(f.run)(Array.canBuildFrom(f.evidence.right))
    }
}
