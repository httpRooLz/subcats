package subcats.data

import scala.reflect.ClassTag

final case class ClassTagFunction1[-A, B](apply: A => B, tag: ClassTag[B])