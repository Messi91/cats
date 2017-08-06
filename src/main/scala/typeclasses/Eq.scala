package typeclasses

import cats.instances.int._
import cats.instances.option._
import cats.instances.string._
import cats.Eq
import models.Cat

object EqInstances {
  implicit val catEqual = Eq.instance[Cat] { (first, second) =>
    import cats.syntax.eq._
    first.age === second.age && first.name === second.name && first.color === second.color
  }
}

object EqSyntax {
  implicit class EqOps[A](first: A) {
    def ===(second: A)(implicit checker: Eq[A]): Boolean = {
      checker.eqv(first, second)
    }

    def =!=(second: A)(implicit checker: Eq[A]): Boolean = {
      !checker.eqv(first, second)
    }
  }
}

object EqApp extends App {
  val cat1 = Cat(name = "Cazzo", age = 3, color = "blue")
  val cat2 = Cat(name = "Doolie", age = 5, color = "orange")
  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]

  def equality: Unit = {
    import EqInstances._
    import EqSyntax._

    val trueEquality = cat1 === cat1
    println(s"true equality = $trueEquality")

    val falseEequality = cat1 === cat2
    println(s"false equality = $falseEequality")

    val optionEquality = optionCat1 === optionCat2
    println(s"option equality = $optionEquality")
  }

  def inequality: Unit = {
    import EqInstances._
    import EqSyntax._

    val trueInequality = cat1 =!= cat2
    println(s"true inequality = $trueInequality")

    val falseInquality = cat1 =!= cat1
    println(s"false inequality = $falseInquality")

    val optionInequality = optionCat1 =!= optionCat2
    println(s"option inequality = $optionInequality")
  }

  equality
  println
  inequality
}
