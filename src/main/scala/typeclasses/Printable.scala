package typeclasses

trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val printableString = new Printable[String] {
    override def format(value: String) = "String = " + value.toString
  }

  implicit val printableInt = new Printable[Int] {
    override def format(value: Int) = "Int = " + value.toString
  }
}


