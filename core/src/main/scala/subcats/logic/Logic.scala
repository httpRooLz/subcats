package subcats.logic

import simulacrum.typeclass

@typeclass trait Logic[T] {
  type Logic
}
object Logic {
  type Aux[T, L] = Logic[T] { type Logic =  L }

  implicit val boolLogic: Logic.Aux[Boolean, Boolean] = new Logic[Boolean] { type Logic = Boolean }
  implicit val unitLogic: Logic.Aux[Unit, Unit] = new Logic[Unit] { type Logic = Unit }
  implicit def funcLogic[A, B, L](implicit B: Logic.Aux[B, L]): Logic.Aux[A => B, A => L]
    = new Logic[A => B] { type Logic = A => L }

  type Classical[T] = Aux[T, Boolean]
}