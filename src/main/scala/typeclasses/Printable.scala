package typeclasses

import models.Cat

trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val printableString = new Printable[String] {
    override def format(value: String) = s"String = $value"
  }

  implicit val printableInt = new Printable[Int] {
    override def format(value: Int) = s"Int = $value"
  }

  implicit val printableCat = new Printable[Cat] {
    override def format(cat: Cat) = s"${cat.name} is a ${cat.age} year-old ${cat.color} cat."
  }
}

object Printable {
  def formatt[A](value: A)(implicit formatter: Printable[A]): String = {
    formatter.format(value)
  }

  def print[A](value: A)(implicit formatter: Printable[A]): Unit = {
    println(formatter.format(value))
  }
}

object PrintableSyntax {
  implicit class PrintOps[A](value: A) {
    def formatt(implicit formatter: Printable[A]): String = {
      formatter.format(value)
    }

    def print(implicit formatter: Printable[A]): Unit = {
      println(formatter.format(value))
    }
  }
}

object PrintableApp extends App {

  val string = "Cazzo likes playing with a ball of string."
  val integer = 9
  val cat = Cat(name = "Cazzo", age = 3, color = "blue")

  def useSingletonFormatter: Unit = {
    import PrintableInstances._

    println(Printable.formatt(string))
    println(Printable.formatt(integer))
    println(Printable.formatt(cat))
  }

  def useSingletonPrinter: Unit = {
    import PrintableInstances._

    Printable.print(string)
    Printable.print(integer)
    Printable.print(cat)
  }

  def useExtensionFormatter: Unit = {
    import PrintableInstances._
    import PrintableSyntax._

    println(string.formatt)
    println(integer.formatt)
    println(cat.formatt)
  }

  def useExtensionPrinter: Unit = {
    import PrintableInstances._
    import PrintableSyntax._

    string.print
    integer.print
    cat.print
  }

  useSingletonFormatter
  println
  useSingletonPrinter
  println
  useExtensionFormatter
  println
  useExtensionPrinter
}
