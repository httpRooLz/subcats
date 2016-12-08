package subcats.category

trait Braided[C[_, _], F[_, _]] extends Associative[C, F] {
  type C0[_]
  def braid[A, B]: F[A, B] C1 F[B, A]
}
object Braided {
  type Aux[F[_, _], C1_[_, _], C0_[_]] = Braided[C1_, F] {
    type C0[A] = C0_[A]
    type C1[A, B] = C1_[A, B]
  }
}
