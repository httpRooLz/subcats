package subcats.monad

import subcats.functor.Functor

trait Monad[I[_], F[_]] extends Functor[F] {
  def I : Functor.Aux[I, C1, C0, D1, D0]
  def pure[A]: D1[I[A], F[A]]
  def bind[A, B](f: D1[I[A], F[B]]): D1[F[A], F[B]]
}
object Monad {
  trait Aux[I[_], F[_], C1[_, _], C0[_], D1[_, _], D0[_]] extends Monad[I, F] with Functor.Aux[F, C1, C0, D1, D0]
}