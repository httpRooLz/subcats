package subcats.category

trait Monoidal[C[_, _], F[_, _]] extends Associative[C, F] {
  type Id

  def idl[A, B]: F[Id, A] C1 A
  def idr[A, B]: F[A, Id] C1 A
  def coidl[A, B]: A C1 F[A, Id]
  def coidr[A, B]: A C1 F[Id, A]
}
object Monoidal {
  type Aux[F[_, _], C1_[_, _], C0_[_], I] = Monoidal[C1_, F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
    type Id = I
  }
}
