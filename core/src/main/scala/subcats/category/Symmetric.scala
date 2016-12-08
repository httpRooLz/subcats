package subcats.category

trait Symmetric[C[_, _], F[_, _]] extends Braided[C, F] {
  type C0[_]
  def swap[A, B]: F[A, B] C1 F[B, A] = braid
}
object Symmetric {
  type Aux[F[_, _], C1_[_, _], C0_[_]] = Symmetric[C1_, F] {
    type C1[A, B] = C1_[A, B]
    type C0[A] = C0_[A]
  }
}