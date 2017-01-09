package subcats.data

import cats.~>
import subcats.category.{Groupoid, Category}

final case class Evidence[T[_], A, B](left: T[A], right: T[B]) {
  def andThen[C](that: Evidence[T, B, C]): Evidence[T, A, C] =
    new Evidence[T, A, C](this.left, that.right)
  def compose[C](that: Evidence[T, C, A]): Evidence[T, C, B] =
    new Evidence[T, C, B](that.left, this.right)

  def flip: Evidence[T, B, A] =
    new Evidence[T, B, A](this.right, this.left)

  def lift[F[_]](f: T ~> λ[X => T[F[X]]]): Evidence[T, F[A], F[B]] =
    new Evidence[T, F[A], F[B]](f.apply(left), f.apply(right))
  def liftT[F[_]](f: T ~> λ[X => T[F[X]]]): Evidence[λ[X => T[F[X]]], A, B] =
    new Evidence[λ[X => T[F[X]]], A, B](f.apply(left), f.apply(right))
}
object Evidence {
  def id[T[_], A](implicit A: T[A]): Evidence[T, A, A] =
    new Evidence[T, A, A](A, A)

  def category[T[_]]: Groupoid.Aux[Evidence[T, ?, ?], T] =
    new Groupoid.Aux[Evidence[T, ?, ?], T] {
      override def id[A](implicit A: T[A]): Evidence[T, A, A] =
        Evidence.id[T, A](A)
      override def compose[A, B, C](bc: Evidence[T, B, C])(ab: Evidence[T, A, B]): Evidence[T, A, C] =
        bc compose ab
      override def flip[A, B](f: Evidence[T, A, B]): Evidence[T, B, A] =
        f.flip
    }
}