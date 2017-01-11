package subcats.data

import cats.Trivial
import subcats.bifunctor.Bifunctor
import subcats.category.{CCC, Cocartesian, Cartesian, Category}

object PartialFunction { self =>
  type ->[A, B] = PartialFunction[A, B]
  type Constraint[A] = Trivial.P1[A]

  type Product[A, B] = (A, B)
  type ProductId = Unit

  type Sum[A, B] = Either[A, B]
  type SumId = Nothing

  type Exp[A, B] = PartialFunction[A, B]

  private[this] def total[A, B](f: A => B): PartialFunction[A, B] = new PartialFunction[A, B] {
    override def isDefinedAt(value: A): Boolean = true
    override def apply(value: A): B = f(value)
  }

  val productBifunctor: Bifunctor.Aux1[Product, ->, Constraint] = new Bifunctor.Aux1[Tuple2, ->, Constraint] {
    override def L: Category.Aux[L1, L0] = self.category
    override def R: Category.Aux[R1, R0] = self.category
    override def C: Category.Aux[C1, C0] = self.category

    override def bimap[LX, LY, RX, RY](left: L1[LX, LY], right: R1[RX, RY]): Product[LX, RX] -> Product[LY, RY] =
      new PartialFunction[Product[LX, RX], Product[LY, RY]] {
        override def isDefinedAt(value: (LX, RX)): Boolean = left.isDefinedAt(value._1) && right.isDefinedAt(value._2)
        override def apply(value: (LX, RX)): (LY, RY) = (left.apply(value._1), right.apply(value._2))
      }
  }

  val sumBifunctor: Bifunctor.Aux1[Sum, ->, Constraint] = new Bifunctor.Aux1[Sum, ->, Constraint] {
    override def L: Category.Aux[L1, L0] = self.category
    override def R: Category.Aux[R1, R0] = self.category
    override def C: Category.Aux[C1, C0] = self.category

    override def bimap[LX, LY, RX, RY](left: LX -> LY, right: RX -> RY): Sum[LX, RX] -> Sum[LY, RY] =
      new PartialFunction[Sum[LX, RX], Sum[LY, RY]] {
        override def isDefinedAt(value: Sum[LX, RX]): Boolean = value match {
          case Left(l) => left.isDefinedAt(l)
          case Right(r) => right.isDefinedAt(r)
        }
        override def apply(value: Sum[LX, RX]): Sum[LY, RY] = value match {
          case Left(l) => Left[LY, RY](left.apply(l))
          case Right(r) => Right[LY, RY](right.apply(r))
        }
      }
  }

//  val cartesian: Cartesian.Aux[->, Constraint, Product, ProductId] = new Cartesian.Aux[->, Constraint, Product, ProductId] {
//    override def C: Category.Aux[C1, C0] = self.category
//    override def bifunctor: Bifunctor.Aux1[Product, C1, C0] = self.productBifunctor
//
//    override def associate[X, Y, Z]: ((X, Y), Z) -> (X, (Y, Z)) = { case ((x, y), z) => (x, (y, z)) }
//    override def diassociate[X, Y, Z]: (X, (Y, Z)) -> ((X, Y), Z) = { case (x, (y, z)) => ((x, y), z) }
//
//    override def braid[A, B]: (A, B) -> (B, A) = { case (a, b) => (b, a) }
//
//    override def coidr[A, B]: A -> (Unit, A) = { case a => ((), a) }
//    override def coidl[A, B]: A -> (A, Unit) = { case a => (a, ()) }
//    override def idr[A, B]: (A, Unit) -> A = { case (a, _) => a }
//    override def idl[A, B]: (Unit, A) -> A = { case (_, a) => a }
//
//    override def fst[A, B]: (A, B) -> A = { case (a, b) => a }
//    override def snd[A, B]: (A, B) -> B = { case (a, b) => b }
//    override def &&&[X, Y, Z](f: X -> Y, g: X -> Z): X -> (Y, Z) = { case x => (f(x), g(x)) }
//    override def diag[A, B]: A -> (A, A) = { case a => (a, a) }
//  }
//
//  val cocartesian: Cocartesian.Aux[->, Constraint, Sum, SumId] = new Cocartesian.Aux[->, Constraint, Sum, SumId] {
//    override def C: Category.Aux[C1, C0] = self.category
//    override def bifunctor: Bifunctor.Aux1[Sum, C1, C0] = self.sumBifunctor
//
//    override def associate[X, Y, Z]: Sum[Sum[X, Y], Z] -> Sum[X, Sum[Y, Z]] = {
//      case Left(xy) => xy match {
//        case Left(x) => Left[X, Either[Y, Z]](x)
//        case Right(y) => Right[X, Either[Y, Z]](Left[Y, Z](y))
//      }
//      case Right(z) => Right[X, Either[Y, Z]](Right[Y, Z](z))
//    }
//    override def diassociate[X, Y, Z]: Sum[X, Sum[Y, Z]] -> Sum[Sum[X, Y], Z] = {
//      case Left(x) => Left[Either[X, Y], Z](Left[X, Y](x))
//      case Right(yz) => yz match {
//        case Left(y) => Left[Either[X, Y], Z](Right[X, Y](y))
//        case Right(z) => Right[Either[X, Y], Z](z)
//      }
//    }
//
//    override def coidr[A, B]: A -> Sum[SumId, A] = total(Right[Nothing, A])
//    override def coidl[A, B]: A -> Sum[A, SumId] = total(Left[A, Nothing])
//    override def inl[A, B]: A -> Sum[A, B] = total(Left[A, B])
//    override def inr[A, B]: B -> Sum[A, B] = total(Right[A, B])
//
//    override def braid[A, B]: Sum[A, B] -> Sum[B, A] = total(_.fold(Right[B, A], Left[B, A]))
//    override def idr[A, B]: Sum[A, SumId] -> A = total(_.fold[A](identity[A], identity[Nothing]))
//    override def idl[A, B]: Sum[SumId, A] -> A = total(_.fold[A](identity[Nothing], identity[A]))
//    override def codiag[A, B]: Sum[A, A] -> A = total(_.fold[A](identity[A], identity[A]))
//
//    override def |||[X, Y, Z](f: X -> Z, g: Y -> Z): Sum[X, Y] -> Z = new (Sum[X, Y] -> Z) {
//      override def isDefinedAt(value: Sum[X, Y]): Boolean = value match {
//        case Left(x) => f.isDefinedAt(x)
//        case Right(y) => g.isDefinedAt(y)
//      }
//      override def apply(value: Sum[X, Y]): Z = value match {
//        case Left(x) => f.apply(x)
//        case Right(y) => g.apply(y)
//      }
//    }
//  }

  val category: Category.Aux[->, Constraint] = new Category.Aux[->, Constraint] {
    override def id[A](implicit A: Constraint[A]): PartialFunction[A, A] =
      new PartialFunction[A, A] {
        override def isDefinedAt(x: A): Boolean = true
        override def apply(a: A): A = a
      }

    override def andThen[A, B, C](g: PartialFunction[A, B], f: PartialFunction[B, C]): PartialFunction[A, C] =
      new PartialFunction[A, C] {
        override def isDefinedAt(x: A): Boolean = g.isDefinedAt(x) && f.isDefinedAt(g(x))
        override def apply(v1: A): C = f(g(v1))
      }
  }
}
