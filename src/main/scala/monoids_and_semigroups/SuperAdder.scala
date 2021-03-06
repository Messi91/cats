package monoids_and_semigroups

import cats.Monoid
import cats.instances.int._
import cats.instances.double._
import cats.instances.option._
import cats.syntax.option._
import cats.syntax.semigroup._
import models.Order

object SuperAdder {
//  def add[A](items: List[A])(implicit monoid: Monoid[A]): A = {
//    items.foldRight(monoid.empty) { (sum, item) =>
//      sum |+| item
//    }
//  }

  def add[A: Monoid](items: List[A]): A = {
    items.foldRight(Monoid[A].empty)(_ |+| _)
  }
}

object SuperAdderInstances {
  implicit val orderAdder = new Monoid[Order] {
    override def combine(x: Order, y: Order) = {
      Order(
        x.totalCost |+| y.totalCost,
        x.quantity |+| y.quantity
      )
    }

    override def empty = Order(0, 0)
  }
}

object SuperAdderSyntax {
  implicit class SuperAdderOps[A: Monoid](items: List[A]) {
    def add: A = SuperAdder.add(items)
  }
}

object SuperAdderApp extends App {
  import SuperAdderInstances._
  import SuperAdderSyntax._

  def addListOfNumbers: Unit = {
    val numbers = List(1, 2, 3, 4, 5)
    val result = numbers.add
    println("addListOfNumbers = " + result)
  }

  def addListOfOptions: Unit = {
    val options = List(1.some, 2.some, 3.some, 4.some, none)
    val result = options.add
    println("addListOfOptions = " + result)
  }

  def addListOfOrders: Unit = {
    val orders = List(Order(1, 2), Order(3, 4), Order(5, 6))
    val result = orders.add
    println("addListOfOrders = " + result)
  }

  addListOfNumbers
  println
  addListOfOptions
  println
  addListOfOrders
}
