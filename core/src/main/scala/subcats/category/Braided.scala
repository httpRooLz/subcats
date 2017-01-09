package subcats.category

trait Braided[->[_, _], F[_, _]] extends Associative[->, F] {
  def braid[A, B]: F[A, B] -> F[B, A]
}
object Braided {
  trait Aux[F[_, _], C1[_, _], C0[_]] extends Braided[C1, F] with Associative.Aux[C1, C0, F]
}
