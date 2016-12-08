package subcats.instances

import cats.Trivial.P1

import subcats.Void
import subcats.bifunctor.Bifunctor
import subcats.category.Cartesian.Aux
import subcats.category._

trait Function1Instances { instances =>
  implicit val tupleBifunctor: Bifunctor.Aux[Tuple2, Function1, P1, Function1, P1, Function1, P1] = new Bifunctor[Tuple2] {
    override type L0[A] = P1[A]
    override type L1[A, B] = Function1[A, B]
    override def L: Category.Aux[L1, L0] = instances.category

    override type R0[A] = P1[A]
    override type R1[A, B] = Function1[A, B]
    override def R: Category.Aux[R1, R0] = instances.category

    override type C0[A] = P1[A]
    override type C1[A, B] = Function1[A, B]
    override def C: Category.Aux[C1, C0] = instances.category

    override def bimap[LX, LY, RX, RY](left: L1[LX, LY], right: R1[RX, RY]): C1[Tuple2[LX, RX], Tuple2[LY, RY]] =
      { case (a, b) => (left(a), right(b)) }
  }

  implicit val eitherBifunctor: Bifunctor.Aux[Either, Function1, P1, Function1, P1, Function1, P1] = new Bifunctor[Either] {
    override type L0[A] = P1[A]
    override type L1[A, B] = Function1[A, B]
    override def L: Category.Aux[L1, L0] = instances.category

    override type R0[A] = P1[A]
    override type R1[A, B] = Function1[A, B]
    override def R: Category.Aux[R1, R0] = instances.category

    override type C0[A] = P1[A]
    override type C1[A, B] = Function1[A, B]
    override def C: Category.Aux[C1, C0] = instances.category

    override def bimap[LX, LY, RX, RY](left: L1[LX, LY], right: R1[RX, RY]): C1[Either[LX, RX], Either[LY, RY]] = {
      case Left(x) => Left[LY, RY](left(x))
      case Right(x) => Right[LY, RY](right(x))
    }
  }

  implicit val cartesian: Cartesian.Aux[Function1, P1, Tuple2, Unit] = new Cartesian[Function1, Tuple2] {
    override type C0[A] = P1[A]
    override def C: Category.Aux[C1, C0] = instances.category

    override def bifunctor: Bifunctor.Aux[Tuple2, C1, C0, C1, C0, C1, C0] = instances.tupleBifunctor

    override def associate[X, Y, Z]: (((X, Y), Z)) => (X, (Y, Z)) = { case ((x, y), z) => (x, (y, z)) }
    override def diassociate[X, Y, Z]: C1[(X, (Y, Z)), ((X, Y), Z)] = { case (x, (y, z)) => ((x, y), z) }

    override def braid[A, B]: ((A, B)) => (B, A) = { case (a, b) => (b, a) }

    override type Id = Unit
    override def coidr[A, B]: A => (Unit, A) = a => ((), a)
    override def coidl[A, B]: A => (A, Unit) = a => (a, ())
    override def idr[A, B]: ((A, Unit)) => A = { case (a, _) => a }
    override def idl[A, B]: ((Unit, A)) => A = { case (_, a) => a }

    override def fst[A, B]: ((A, B)) => A = { case (a, b) => a }
    override def snd[A, B]: ((A, B)) => B = { case (a, b) => b }
    override def &&&[X, Y, Z](f: X => Y, g: X => Z): X => (Y, Z) = x => (f(x), g(x))
    override def diag[A, B]: A => (A, A) = a => (a, a)
  }

  implicit val cocartesian: Cocartesian.Aux[Function1, P1, Either, Void] = new Cocartesian[Function1, Either] {
    override type C0[A] = P1[A]
    override def C: Category.Aux[C1, C0] = instances.category

    override def bifunctor: Bifunctor.Aux[Either, C1, C0, C1, C0, C1, C0] = instances.eitherBifunctor

    override def associate[X, Y, Z]: C1[Either[Either[X, Y], Z], Either[X, Either[Y, Z]]] = {
      case Left(xy) => xy match {
        case Left(x) => Left[X, Either[Y, Z]](x)
        case Right(y) => Right[X, Either[Y, Z]](Left[Y, Z](y))
      }
      case Right(z) => Right[X, Either[Y, Z]](Right[Y, Z](z))
    }
    override def diassociate[X, Y, Z]: C1[Either[X, Either[Y, Z]], Either[Either[X, Y], Z]] = {
      case Left(x) => Left[Either[X, Y], Z](Left[X, Y](x))
      case Right(yz) => yz match {
        case Left(y) => Left[Either[X, Y], Z](Right[X, Y](y))
        case Right(z) => Right[Either[X, Y], Z](z)
      }
    }

    override def braid[A, B]: C1[Either[A, B], Either[B, A]] = e => e.swap

    override type Id = Void
    override def coidr[A, B]: C1[A, Either[Void, A]] = Right[Void, A]
    override def coidl[A, B]: C1[A, Either[A, Void]] = Left[A, Void]
    override def idr[A, B]: C1[Either[A, Void], A] = e => e.fold(a => a, v => v)
    override def idl[A, B]: C1[Either[Void, A], A] = e => e.fold(v => v, a => a)

    override def inl[A, B]: C1[A, Either[A, B]] = Left[A, B]
    override def inr[A, B]: C1[B, Either[A, B]] = Right[A, B]
    override def |||[X, Y, Z](f: C1[X, Z], g: C1[Y, Z]): C1[Either[X, Y], Z] = e => e.fold(f, g)
    override def codiag[A, B]: C1[Either[A, A], A] = e => e.fold(identity, identity)
  }

  implicit val category: CCC.Aux[Function1, P1, Tuple2, Unit, Function1] = new CCC[Function1] {
    override type C0[A]         = P1[A]
    override type ProductId     = Unit
    override type Product[A, B] = Tuple2[A, B]
    override type Exp[A, B]     = Function1[A, B]

    override def id[A](implicit A: P1[A]): C1[A, A] = identity[A]
    override def compose[A, B, C](bc: C1[B, C])(ab: C1[A, B]): C1[A, C] = bc.compose(ab)

    override def cartesian: Aux[Function1, P1, Tuple2, Unit] = instances.cartesian

    override def apply[A, B]: Product[Exp[A, B], A] Function1 B = { case (f, a) => f(a) }
    override def curry[X, Y, Z](f: C1[Product[X, Y], Z]): X Function1 Exp[Y, Z] = x => y => f((x, y))
    override def uncurry[X, Y, Z](f: C1[X, Exp[Y, Z]]): Product[X, Y] Function1 Z = { case (x, y) => f(x)(y) }
  }
}
