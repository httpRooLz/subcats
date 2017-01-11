package subcats.data

import cats.Trivial
import subcats.category.{Groupoid, Category}

final class Indiscrete[A, B] private[Indiscrete] () {
  def compose[C](ca: Indiscrete[C, A]): Indiscrete[C, B] = Indiscrete.get
  def andThen[C](bc: Indiscrete[B, C]): Indiscrete[A, C] = Indiscrete.get
  def flip: Indiscrete[B, A] = Indiscrete.get
  def lift[F[_]]: Indiscrete[F[A], F[B]] = Indiscrete.get
}

object Indiscrete {
  private[this] val anyIndiscrete: Indiscrete[Any, Any] = new Indiscrete[Any, Any]
  def get[A, B]: Indiscrete[A, B] = anyIndiscrete.asInstanceOf[Indiscrete[A, B]]

  type ->[A, B] = Indiscrete[A, B]
  type Constraint[A] = Trivial.P1[A]

  def groupoid: Groupoid.Aux[->, Constraint] = new Groupoid.Aux[->, Constraint] {
    override def id[A](implicit A: Constraint[A]): A -> A = get[A, A]
    override def andThen[A, B, C](ab: A -> B, bc: B -> C): A -> C = get[A, C]
    override def flip[A, B](f: A -> B): B -> A = get[B, A]
  }
}
