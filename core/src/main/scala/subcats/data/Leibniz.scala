package subcats.data

import cats.Trivial.P1
import subcats.category.Category.Aux
import subcats.category._
import subcats.functor.Endofunctor

trait Leibniz[A, B] { ab =>
  def subst[F[_]](fa: F[A]): F[B]

  def compose[C](ca: Leibniz[C, A]): Leibniz[C, B] = new Leibniz[C, B] {
    def subst[F[_]](fa: F[C]): F[B] = ab.subst[F](ca.subst[F](fa))
  }
  def andThen[C](ca: Leibniz[B, C]): Leibniz[A, C] = ca.compose(ab)

  def flip: Leibniz[B, A] =
    subst[Leibniz[?, A]](Leibniz.refl[A])

  def coerce(a: A): B =
    subst[λ[X => X]](a)

  def lift[F[_]]: Leibniz[F[A], F[B]] =
    subst[λ[X => Leibniz[F[A], F[X]]]](Leibniz.refl[F[A]])
}

object Leibniz {
  type ===[A, B] = Leibniz[A, B]

  def refl[A]: Leibniz[A, A] =
    new Leibniz[A, A] { def subst[F[_]](fa: F[A]): F[A] = fa }

  val groupoid: Groupoid.AuxT[Leibniz[?, ?]] with Concrete.AuxT[Leibniz[?, ?]] =
    new Groupoid.AuxT[Leibniz[?, ?]] with Concrete.AuxT[Leibniz[?, ?]] {
      override def id[A](implicit A: P1[A]): Leibniz[A, A] =
        Leibniz.refl[A]
      override def compose[A, B, C](bc: Leibniz[B, C])(ab: Leibniz[A, B]): Leibniz[A, C] =
        bc.compose(ab)
      override def flip[A, B](ab: Leibniz[A, B]): Leibniz[B, A] =
        ab.flip
      override def concretize[A, B](ab: Leibniz[A, B]): (A, P1[A]) => (B, P1[B]) =
        (a, p) => ab.subst[λ[X => (X, P1[X])]]((a, p))
    }

  def functor[F[_]]: Endofunctor.AuxT[F, Leibniz] = new Endofunctor.AuxT[F, Leibniz] {
    override def C: Aux[C1, C0] = groupoid
    override def map[A, B](f: Leibniz[A, B]): Leibniz[F[A], F[B]] = f.lift[F]
  }
}