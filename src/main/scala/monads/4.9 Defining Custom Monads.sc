import cats.Monad
import cats.syntax.either._
import scala.annotation.tailrec
import functors.{Branch, Leaf, Tree}
import functors.TreeConstructors._

val optionMonad = new Monad[Option] {
  override def flatMap[A, B](opt: Option[A])(fn: (A) => Option[B]): Option[B] = {
    opt flatMap fn
  }

  override def pure[A](opt: A): Option[A] = {
    Some(opt)
  }

  @tailrec
  override def tailRecM[A, B](a: A)(fn: (A) => Option[Either[A, B]]): Option[B] = {
    fn(a) match {
      case None => None
      case Some(Left(a1)) => tailRecM(a1)(fn)
      case Some(Right(b)) => Some(b)
    }
  }
}

val pureOption = optionMonad.pure(20)

val flatMappedOption = optionMonad.flatMap(pureOption)(x => Some((x + 34).toString))

val tailRecOption = optionMonad.tailRecM(20)(x => Option((x + 34).toString.asRight[Int]))



implicit val treeMonad = new Monad[Tree] {
  override def pure[A](a: A): Tree[A] = {
    Leaf(a)
  }

  override def flatMap[A, B](tree: Tree[A])(fn: (A) => Tree[B]): Tree[B] = {
    tree match {
      case Branch(left, right) => Branch(flatMap(left)(fn), flatMap(right)(fn))
      case Leaf(value) => fn(value)
    }
  }

  override def tailRecM[A, B](arg: A)(func: (A) => Tree[Either[A, B]]): Tree[B] = {
    func(arg) match {
      case Branch(left, right) =>
        Branch(
          flatMap(left) {
            case Left(left) => tailRecM(left)(func)
            case Right(left) => pure(left)
          },
          flatMap(right) {
            case Left(right) => tailRecM(right)(func)
            case Right(right) => pure(right)
          }
        )

      case Leaf(Left(value)) => tailRecM(value)(func)

      case Leaf(Right(value)) => Leaf(value)
    }
  }
}

object TreeMonadSyntax {
  implicit class TreeOps[A](tree: Tree[A]) {
    def flatMap[B](func: (A) => Tree[B])(implicit monad: Monad[Tree]): Tree[B] = {
      monad.flatMap(tree)(func)
    }
  }
}


import cats.syntax.functor._
import cats.syntax.flatMap._
import TreeMonadSyntax._

branch(leaf(100), leaf(200)).flatMap(x => branch(leaf(x - 1), leaf(x + 1)))

for {
  a <- branch(leaf(100), leaf(200))
  b <- branch(leaf(a - 10), leaf(a + 10))
  c <- branch(leaf(b - 1), leaf(b + 1))
} yield c
