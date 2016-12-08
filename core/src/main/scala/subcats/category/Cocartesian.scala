package subcats.category

trait Cocartesian[C[_, _], F[_, _]] extends Monoidal[C, F] with Symmetric[C, F] {
  def inl[A, B]: A C1 F[A, B]
  def inr[A, B]: B C1 F[A, B]
  def codiag[A, B]: F[A, A] C1 A
  def |||[X, Y, Z](f: X C1 Z, g: Y C1 Z): F[X, Y] C1 Z
}
object Cocartesian {
  type Aux[C1_[_, _], C0_[_], F[_, _], I] = Cocartesian[C1_, F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
    type Id = I
  }
}