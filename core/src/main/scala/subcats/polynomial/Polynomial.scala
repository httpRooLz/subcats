package subcats.polynomial

import cats.Functor
import simulacrum.typeclass

object Polynomial {
sealed abstract class TypeF protected() {
  type F[A]
  def F: Functor[F]
}
object TypeF {
  sealed abstract class Aux[F_[_]] extends TypeF {
    type F[A] = F_[A]
    def F: Functor[F]
  }

  implicit class TypeFOps[T <: TypeF](t: T) {
    def :+:[U <: TypeF](u: U): :+:[U, T] = new :+:(u, t)
    def :*:[U <: TypeF](u: U): :*:[U, T] = new :*:(u, t)
  }

  final class I extends Aux[λ[X => X]] {
    val F = new Functor[λ[X => X]] {
      override def map[A, B](a: A)(f: (A) => B): B = f(a)
    }
  }
  def I: I = new I()

  final class K[A] extends Aux[λ[X => A]] {
    val F = new Functor[λ[X => A]] {
      override def map[X, Y](a: A)(f: X => Y): A = a
    }
  }
  def K[A]: K[A] = new K[A]()

  final class :+:[A <: TypeF, B <: TypeF](A: A, B: B) extends Aux[λ[X => Either[A#F[X], B#F[X]]]] {
    val AF: Functor[A#F] = A.F.asInstanceOf[Functor[A#F]]
    val BF: Functor[B#F] = B.F.asInstanceOf[Functor[B#F]]

    val F = new Functor[λ[X => Either[A#F[X], B#F[X]]]] {
      override def map[X, Y](v: Either[A#F[X], B#F[X]])(f: (X) => Y): Either[A#F[Y], B#F[Y]] = v match {
        case Left(a) => Left(AF.map(a)(f))
        case Right(b) => Right(BF.map(b)(f))
      }
    }
  }

  final class :*:[A <: TypeF, B <: TypeF](A: A, B: B) extends Aux[λ[X => (A#F[X], B#F[X])]] {
    val AF: Functor[A#F] = A.F.asInstanceOf[Functor[A#F]]
    val BF: Functor[B#F] = B.F.asInstanceOf[Functor[B#F]]

    val F = new Functor[λ[X => (A#F[X], B#F[X])]] {
      override def map[X, Y](v: (A#F[X], B#F[X]))(f: (X) => Y): (A#F[Y], B#F[Y]) = v match {
        case (a, b) => (AF.map(a)(f), BF.map(b)(f))
      }
    }
  }
}

import TypeF._
type                           ListF[A, B]  = (K[Unit] :+: (K[A] :*: I))#F[B]
implicit def ListF[A]: Functor[ListF[A, ?]] = (K[Unit] :+: (K[A] :*: I)).F

  type Algebra[F[_], A] = F[A] => A
  type Coalgebra[F[_], A] = A => F[A]

  @typeclass trait Recursive[T] {
    type Base[_]
    def project(t: T): Base[T]
  }
  object Recursive {
    trait Aux[T, B[_]] extends Recursive[T] {
      type Base[A] = B[A]
    }
  }

  def cata[T, F[_], A](t: T, f: Algebra[F, A])(implicit T: Recursive.Aux[T, F], F: Functor[F]): A = {
    def go(t: T): A = f(F.map(T.project(t))(go))
    go(t)
  }

  final case class Fix[F[_]](unFix: F[Fix[F]])
  implicit def fixRecursive[F[_]]: Recursive.Aux[Fix[F], F] = new Recursive.Aux[Fix[F], F] {
    override def project(t: Fix[F]): F[Fix[F]] = t.unFix
  }

  type List[A] = Fix[ListF[A, ?]]
  implicit def listIsRecursive[A]: Recursive.Aux[List[A], ListF[A, ?]] =
    fixRecursive[ListF[A, ?]]

//  implicitly[Functor[ListF[Int, ?]]]
//  implicitly[Recursive[List[Int]]]

  def size[A](list: List[A]): Int = {
    def go(l: ListF[A, Int]): Int = l match {
      case Left(_) => 0
      case Right((_, s)) => s + 1
    }
    cata[List[A], ListF[A, ?], Int](list, go)
  }
}