package subcats.category

trait Cartesian[C[_, _], F[_, _]] extends Monoidal[C, F] with Symmetric[C, F] {
  def fst[A, B]: F[A, B] C A
  def snd[A, B]: F[A, B] C B
  def diag[A, B]: A C F[A, A]
  def &&&[X, Y, Z](f: X C Y, g: X C Z): X C F[Y, Z]
}
object Cartesian {
  type Aux[C1_[_, _], C0_[_], F[_, _], I] = Cartesian[C1_, F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
    type Id = I
  }
}
