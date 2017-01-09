package subcats.category

trait Monoidal[->[_, _], F[_, _]] extends Associative[->, F] {
  type Id

  def idl[A, B]: F[Id, A] -> A
  def idr[A, B]: F[A, Id] -> A
  def coidl[A, B]: A -> F[A, Id]
  def coidr[A, B]: A -> F[Id, A]
}
object Monoidal {
  trait Aux[F[_, _], C1[_, _], C0[_], I] extends Monoidal[C1, F] with Associative.Aux[C1, C0, F] {
    type Id = I
  }
}
