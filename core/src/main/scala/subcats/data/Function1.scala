package subcats.data

import cats.Trivial
import subcats.bifunctor.Bifunctor
import subcats.category._

object Function1 { self =>
  type ->[A, B] = A => B
  type Constraint[A] = Trivial.P1[A]

  type Product[A, B] = (A, B)
  type ProductId = Unit

  type Sum[A, B] = Either[A, B]
  type SumId = Nothing

  type Exp[A, B] = A => B

  val productBifunctor: Bifunctor.Aux1[Product, ->, Constraint] = new Bifunctor.Aux1[Tuple2, ->, Constraint] {
    override def L: Category.Aux[L1, L0] = self.category
    override def R: Category.Aux[R1, R0] = self.category
    override def C: Category.Aux[C1, C0] = self.category

    override def bimap[LX, LY, RX, RY](left: L1[LX, LY], right: R1[RX, RY]): Product[LX, RX] -> Product[LY, RY] = {
      case (a, b) => (left(a), right(b))
    }
  }

  val sumBifunctor: Bifunctor.Aux1[Sum, ->, Constraint] = new Bifunctor.Aux1[Sum, ->, Constraint] {
    override def L: Category.Aux[L1, L0] = self.category
    override def R: Category.Aux[R1, R0] = self.category
    override def C: Category.Aux[C1, C0] = self.category

    override def bimap[LX, LY, RX, RY](left: L1[LX, LY], right: R1[RX, RY]): Sum[LX, RX] -> Sum[LY, RY] = {
      case Left(x) => Left[LY, RY](left(x))
      case Right(x) => Right[LY, RY](right(x))
    }
  }

  val cartesian: Cartesian.Aux[->, Constraint, Product, ProductId] = new Cartesian.Aux[->, Constraint, Product, ProductId] {
    override def C: Category.Aux[C1, C0] = self.category
    override def bifunctor: Bifunctor.Aux1[Product, C1, C0] = self.productBifunctor

    override def associate[X, Y, Z]: ((X, Y), Z) -> (X, (Y, Z)) = { case ((x, y), z) => (x, (y, z)) }
    override def diassociate[X, Y, Z]: (X, (Y, Z)) -> ((X, Y), Z) = { case (x, (y, z)) => ((x, y), z) }

    override def braid[A, B]: (A, B) -> (B, A) = { case (a, b) => (b, a) }

    override def coidr[A, B]: A -> (Unit, A) = { case a => ((), a) }
    override def coidl[A, B]: A -> (A, Unit) = { case a => (a, ()) }
    override def idr[A, B]: (A, Unit) -> A = { case (a, _) => a }
    override def idl[A, B]: (Unit, A) -> A = { case (_, a) => a }

    override def fst[A, B]: (A, B) -> A = { case (a, b) => a }
    override def snd[A, B]: (A, B) -> B = { case (a, b) => b }
    override def &&&[X, Y, Z](f: X -> Y, g: X -> Z): X -> (Y, Z) = { case x => (f(x), g(x)) }
    override def diag[A, B]: A -> (A, A) = { case a => (a, a) }
  }

  val cocartesian: Cocartesian.Aux[->, Constraint, Sum, SumId] = new Cocartesian.Aux[->, Constraint, Sum, SumId] {
    override def C: Category.Aux[C1, C0] = self.category
    override def bifunctor: Bifunctor.Aux1[Sum, C1, C0] = self.sumBifunctor

    override def associate[X, Y, Z]: Sum[Sum[X, Y], Z] -> Sum[X, Sum[Y, Z]] = {
      case Left(xy) => xy match {
        case Left(x) => Left[X, Either[Y, Z]](x)
        case Right(y) => Right[X, Either[Y, Z]](Left[Y, Z](y))
      }
      case Right(z) => Right[X, Either[Y, Z]](Right[Y, Z](z))
    }
    override def diassociate[X, Y, Z]: Sum[X, Sum[Y, Z]] -> Sum[Sum[X, Y], Z] = {
      case Left(x) => Left[Either[X, Y], Z](Left[X, Y](x))
      case Right(yz) => yz match {
        case Left(y) => Left[Either[X, Y], Z](Right[X, Y](y))
        case Right(z) => Right[Either[X, Y], Z](z)
      }
    }

    override def coidr[A, B]: A -> Sum[SumId, A] = Right[Nothing, A]
    override def coidl[A, B]: A -> Sum[A, SumId] = Left[A, Nothing]
    override def inl[A, B]: A -> Sum[A, B] = Left[A, B]
    override def inr[A, B]: B -> Sum[A, B] = Right[A, B]

    override def braid[A, B]: Sum[A, B] -> Sum[B, A] = _.fold(Right[B, A], Left[B, A])
    override def idr[A, B]: Sum[A, SumId] -> A = _.fold[A](identity[A], identity[Nothing])
    override def idl[A, B]: Sum[SumId, A] -> A = _.fold[A](identity[Nothing], identity[A])
    override def codiag[A, B]: Sum[A, A] -> A = _.fold[A](identity[A], identity[A])

    override def |||[X, Y, Z](f: X -> Z, g: Y -> Z): Sum[X, Y] -> Z = _.fold(f, g)
  }

  val category: CCC.Aux[->, Constraint, Product, ProductId, Exp]
    with HasInitialObject.Aux[->, Constraint, SumId]
    with HasTerminalObject.Aux[->, Constraint, ProductId] =
    new CCC.Aux[->, Constraint, Product, ProductId, Exp]
      with HasInitialObject.Aux[->, Constraint, SumId]
      with HasTerminalObject.Aux[->, Constraint, ProductId]
    {
      override def id[A](implicit A: Constraint[A]): A -> A = identity[A]
      override def andThen[A, B, C](ab: A -> B, bc: B -> C): A -> C = bc.compose(ab)

      override def cartesian: Cartesian.Aux[->, Constraint, Product, ProductId] = self.cartesian

      override def apply[A, B]: Product[Exp[A, B], A] -> B = { case (f, a) => f(a) }
      override def curry[X, Y, Z](f: C1[Product[X, Y], Z]): X -> Exp[Y, Z] = x => y => f((x, y))
      override def uncurry[X, Y, Z](f: C1[X, Exp[Y, Z]]): Product[X, Y] -> Z = { case (x, y) => f(x)(y) }

      override def terminal: Constraint[Terminal] = Trivial.catsTrivialInstance
      override def terminate[A](implicit A: Constraint[A]): A -> Terminal = a => ()

      override def initial: Constraint[Initial] = Trivial.catsTrivialInstance
      override def initiate[A](implicit A: Constraint[A]): Initial -> A = null
    }
}
