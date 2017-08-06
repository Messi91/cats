package typeclasses

import cats.Show
import cats.instances.int._
import cats.instances.string._
import models.Cat

object ShowInstances {
  implicit val showCat = Show.show[Cat] { cat =>
    import cats.syntax.show._
    s"${cat.name.show} is a ${cat.age.show} year-old ${cat.color.show} cat."
  }
}

object ShowSyntax {
  implicit class ShowOps[A](value: A) {
    def show(implicit showoff: Show[A]): String = {
      showoff.show(value)
    }
  }
}

object ShowApp extends App {

  val cat = Cat(name = "Cazzo", age = 3, color = "blue")

  def useSingleton: Unit = {
    val showInt = Show.apply[Int]
    val showString = Show.apply[String]
    val showCat = Show.apply[Cat](ShowInstances.showCat)

    println(s"Showing Int = ${showInt.show(123)}")
    println(s"Showing String = ${showString.show("abc")}")
    println(s"Showing Cat = ${showCat.show(cat)}")
  }

  def useSyntax: Unit = {
    import ShowSyntax._

    val showInt = 123.show
    val showString = "abc".show
    val showCat = cat.show(ShowInstances.showCat)

    println(s"Showing Int = $showInt")
    println(s"Showing String = $showString")
    println(s"Showing Cat = $showCat")
  }

  useSingleton
  println
  useSyntax
}
