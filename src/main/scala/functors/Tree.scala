package functors

import cats.Functor
import cats.syntax.functor._

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object TreeInstances {
  implicit val treeFunctor = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: (A) => B): Tree[B] =
      fa match {
        case Branch(left, right) => Branch(map(left)(f), map(right)(f))
        case Leaf(value) => Leaf(f(value))
      }
  }
}

object TreeConstructors {
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = {
    Branch(left, right)
  }

  def leaf[A](value: A): Tree[A] = {
    Leaf(value)
  }
}

object TreeApp extends App {

  import TreeConstructors._
  import TreeInstances._

  def testFruitTree: Unit = {
    val appleTree = branch(
      left = Branch(
        left = Branch(
          left = Branch(
            left = Leaf("Apple"),
            right = Leaf("Apple")
          ),
          right = Leaf("Apple")
        ),
        right = Leaf("Apple")
      ),
      right = Leaf("Apple")
    )

    val bananaMagic = (_: String) => "Banana"

    val bananaTree = appleTree.map(bananaMagic)

    println("Testing fruit tree:")
    println("Apple tree = " + appleTree)
    println("Banana tree = " + bananaTree)
  }

  def testIntTree: Unit = {
    val leafResult = leaf(100).map(_ * 2)
    val branchResult = branch(Leaf(10), Leaf(20)).map(_ * 2)

    println("Testing integer tree:")
    println("Leaf = " + leafResult)
    println("Branch = " + branchResult)
  }

  testFruitTree
  println
  testIntTree
}
