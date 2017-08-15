package typeclasses

import models.{Box, Cat}

trait Printable[A] {
  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] = {
    val self = this
    new Printable[B] {
      override def format(value: B) = self.format(func(value))
    }
  }
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

  implicit def printableBox[A](implicit printable: Printable[A]): Printable[Box[A]] = {
    printable.contramap[Box[A]](_.value)
  }
}

object Printable {
  def formatt[A](value: A)(implicit formatter: Printable[A]): String = {
    formatter.format(value)
  }

  def print[A](value: A)(implicit formatter: Printable[A]): Unit = {
    println(formatter.format(value))
  }

  def contramap[A, B](func: (B => A))(implicit formatter: Printable[A]): Printable[B] = {
    formatter.contramap(func)
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
  val double = 3.14

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

  def useContramap: Unit = {
    import PrintableInstances._

    val double2string = Printable.contramap((x: Double) => x.toString)
    val double2integer = Printable.contramap((x: Double) => x.toInt)

    println(double2string.format(double))
    println(double2integer.format(double))
  }

  def useBox: Unit = {
    import PrintableInstances._
    import PrintableSyntax._

    val stringBox = Box(string)
    val integerBox = Box(integer)
    val catBox = Box(cat)

    stringBox.print
    integerBox.print
    catBox.print
  }

  useSingletonFormatter
  println
  useSingletonPrinter
  println
  useExtensionFormatter
  println
  useExtensionPrinter
  println
  useContramap
  println
  useBox
}
