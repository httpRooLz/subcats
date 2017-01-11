package subcats.syntax

import subcats.category._
import subcats.data.Leibniz.===

trait CategorySyntax {
  implicit class CategorySyntax[->[_, _], A, B](val ab: A -> B) {
    def compose[C](ca: C -> A)(implicit C: Semicategory[->]): C -> B = C.andThen(ca, ab)
    def andThen[C](bc: B -> C)(implicit C: Semicategory[->]): A -> C = C.andThen(ab, bc)
    def flip(implicit C: Groupoid[->]): B -> A = C.flip(ab)

    def associateR[X, Y, Z, F[_, _]]
    (implicit C: Semicategory[->], A: Associative[->, F], ev: B === F[F[X, Y], Z]): A -> F[X, F[Y, Z]]
      = C.andThen(ev.subst[λ[X => A -> X]](ab), A.associate[X, Y, Z])
    def diassociateR[X, Y, Z, F[_, _]]
    (implicit C: Semicategory[->], A: Associative[->, F], ev: B === F[X, F[Y, Z]]): A -> F[F[X, Y], Z]
      = C.andThen(ev.subst[λ[X => A -> X]](ab), A.diassociate[X, Y, Z])

    def braidR[X, Y, F[_, _]]
    (implicit C: Semicategory[->], B: Braided[->, F], ev: B === F[X, Y]): A -> F[Y, X]
      = C.andThen(ev.subst[λ[X => A -> X]](ab), B.braid[X, Y])

    def curry[X, Y, Z, C0[_], P[_, _], PI, E[_, _]]
    (f: P[X, Y] -> Z)(implicit C: CCC.Aux[->, C0, P, PI, E]): X -> E[Y, Z] =
      C.curry(f)
    def uncurry[X, Y, Z, C0[_], P[_, _], PI, E[_, _]]
    (f: X -> E[Y, Z])(implicit C: CCC.Aux[->, C0, P, PI, E]): P[X, Y] -> Z =
      C.uncurry(f)
  }
}
