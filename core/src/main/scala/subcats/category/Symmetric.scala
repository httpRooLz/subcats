package subcats.category

trait Symmetric[C[_, _], F[_, _]] extends Braided[C, F] {
  type C0[_]
  def swap[A, B]: F[A, B] C1 F[B, A] = braid
}
object Symmetric {
  trait Aux[F[_, _], C1[_, _], C0[_]] extends Symmetric[C1, F] with Braided.Aux[F, C1, C0]
}