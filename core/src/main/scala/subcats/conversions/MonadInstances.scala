package subcats.conversions

import cats.Id
import cats.Trivial._
import subcats.monad.Monad

trait MonadInstances {
  implicit def monad2monad[F[_]](implicit F: Monad.Aux[Id, F, Function1, P1, Function1, P1]): cats.Monad[F] =
    new cats.Monad[F] {
      override def pure[A](x: A): F[A] = F.pure.apply(x)
      override def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = F.bind[A, B](f).apply(fa)
      override def tailRecM[A, B](a: A)(f: A => F[Either[A, B]]): F[B] = ???
    }
}
