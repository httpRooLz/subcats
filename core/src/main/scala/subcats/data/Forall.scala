package subcats.data

import cats.~>

trait Forall[F[_]] { f =>
  def run[A]: F[A]

  def mapK[G[_]](fg: F ~> G): Forall[G] = new Forall[G] {
    override def run[A]: G[A] = fg.apply(f.run[A])
  }
}

trait ForallK[F[_[_]]] {
  def run[A[_]]: F[A]
}