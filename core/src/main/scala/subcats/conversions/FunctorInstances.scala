package subcats.conversions

import cats.Trivial._
import cats.functor.{Invariant, Contravariant}
import subcats.data.{ProdCat, Dual}
import subcats.functor.Functor

trait FunctorInstances {
  implicit def functor2functor[F[_]]
  (implicit F: Functor.Aux[F, Function1, P1, Function1, P1]): cats.Functor[F] =
    new cats.Functor[F] {
      override def map[A, B](fa: F[A])(f: A => B): F[B] = F.map[A, B](f).apply(fa)
    }

  implicit def functor2contravariant[F[_]]
  (implicit F: Functor.Aux[F, Dual[Function1, ?, ?], P1, Function1, P1]): cats.functor.Contravariant[F] =
    new Contravariant[F] {
      override def contramap[A, B](fa: F[A])(f: B => A): F[B] = F.map[A, B](Dual(f)).apply(fa)
    }

  implicit def functor2invariant[F[_]]
  (implicit F: Functor.Aux[F, ProdCat[Function1, Dual[Function1, ?, ?], ?, ?], P1, Function1, P1]): cats.functor.Invariant[F] =
    new Invariant[F] {
      override def imap[A, B](fa: F[A])(f: A => B)(g: B => A): F[B] = F.map(ProdCat(f, Dual(g))).apply(fa)
    }
}
