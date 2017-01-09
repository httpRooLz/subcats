package subcats.data

trait Exists[F[_]] {
  type A; def run: F[A]
}
