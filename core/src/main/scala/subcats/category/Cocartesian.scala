package subcats.category

trait Cocartesian[->[_, _], F[_, _]] extends Monoidal[->, F] with Symmetric[->, F] {
  def inl[A, B]: A -> F[A, B]
  def inr[A, B]: B -> F[A, B]
  def codiag[A, B]: F[A, A] -> A
  def |||[X, Y, Z](f: X C1 Z, g: Y C1 Z): F[X, Y] -> Z
}
object Cocartesian {
  trait Aux[C1[_, _], C0[_], F[_, _], I]
    extends Cocartesian[C1, F]
      with Monoidal.Aux[F, C1, C0, I]
      with Symmetric.Aux[F, C1, C0]
}