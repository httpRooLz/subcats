package subcats.syntax

import subcats.category.{Category, CCC}

trait CategorySyntax {
  implicit class CategorySyntax[->[_, _], A, B](val ab: A -> B) {
    def compose[C](ca: C -> A)(implicit category: Category[->]): C -> B = category.compose(ab)(ca)
    // def curry[X, Y, Z](f: Product[X, Y] -> Z): X -> Exp[Y, Z]
    // def uncurry[X, Y, Z](f: C1[X, Exp[Y, Z]]): Product[X, Y] -> Z
  }
}
