package subcats

object `package` {
  type Void = Nothing with ({type Tag = Nothing })
  def absurd[A]: Void => A = ???
  def unit[A]: A => Unit = a => ()
}