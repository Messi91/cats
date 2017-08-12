package monoids

import cats.Monoid

object MonoidInstances {
  implicit val booleanAndMonoid = new Monoid[Boolean] {
    override def combine(x: Boolean, y: Boolean) = x && y
    override def empty = true
  }

  implicit val booleanOrMonoid = new Monoid[Boolean] {
    override def combine(x: Boolean, y: Boolean) = x || y
    override def empty = false
  }

  implicit val booleanEitherMonoid = new Monoid[Boolean] {
    override def combine(x: Boolean, y: Boolean) = {
      (x && !y) || (!x && y)
    }
    override def empty = false
  }

  implicit val booleanXnorMonoid = new Monoid[Boolean] {
    override def combine(x: Boolean, y: Boolean) = {
      (!x || y) && (x || !y)
    }
    override def empty = true
  }
}

object BooleanMonoidSyntax {
  import MonoidInstances._

  implicit class MonoidOps(x: Boolean) {
    def AND(y: Boolean): Boolean = booleanAndMonoid.combine(x, y)
    def OR(y: Boolean): Boolean = booleanOrMonoid.combine(x, y)
    def XOR(y: Boolean): Boolean = booleanEitherMonoid.combine(x, y)
    def XNOR(y: Boolean): Boolean = booleanXnorMonoid.combine(x, y)
  }
}

object BooleanMonoidApp extends App {
  import BooleanMonoidSyntax._

  def testAnd: Unit = {
    println("--Identity--")
    val identityRule = (true AND false) == (false AND true)
    println("(true AND false) == (false AND true): " + identityRule)
    println
    println("--Associativity--")
    val associativityRule = ((true AND false) AND true) == (true AND (false AND true))
    println("((true AND false) AND true) == (true AND (false AND true)): " + associativityRule)
  }

  testAnd
}
