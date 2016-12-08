package subcats

object `package` {
  type Void = Nothing with ({type Tag = Nothing })
  def absurd[A]: Void => A = ???
  def unit[A]: A => Unit = a => ()

  trait Forall[F[_]] { def apply[A]: F[A] }
  trait Exists[F[_]] { type A; def apply: F[A] }
}