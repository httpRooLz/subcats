package subcats.category

trait Cartesian[->[_, _], F[_, _]] extends Monoidal[->, F] with Symmetric[->, F] {
  def fst[A, B]: F[A, B] -> A
  def snd[A, B]: F[A, B] -> B
  def diag[A, B]: A -> F[A, A]
  def &&&[X, Y, Z](f: X -> Y, g: X -> Z): X -> F[Y, Z]
}
object Cartesian {
  trait Aux[C1[_, _], C0[_], F[_, _], I]
    extends Cartesian[C1, F]
      with Monoidal.Aux[F, C1, C0, I]
      with Symmetric.Aux[F, C1, C0]
}
