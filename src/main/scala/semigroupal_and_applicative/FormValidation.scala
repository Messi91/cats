package semigroupal_and_applicative

import cats.data._
import cats.implicits._
import cats.instances._
import cats.syntax._
import models.User

object FormValidation extends App {

  def readForm(form: Map[String, String]): Either[List[String], User] =
    (readName(form).toValidated, readAge(form).toValidated).mapN(User).toEither

  def readName(form: Map[String, String]): Either[List[String], String] =
    for {
      name <- getValue(form, "name")
      _ <- nonBlank(name)
    } yield name

  def readAge(form: Map[String, String]): Either[List[String], Int] =
    for {
      value <- getValue(form, "age")
      age <- parseInt(value)
      _ <- nonNegative(age)
    } yield age

  def getValue(form: Map[String, String], name: String): Either[List[String], String] =
    Validated.fromOption(form.get(name), List(s"$name not found.")).toEither

  def parseInt(string: String): Either[List[String], Int] =
    Validated.catchOnly[NumberFormatException](string.toInt).leftMap(_.getMessage :: Nil).toEither

  def nonBlank(string: String): Either[List[String], String] =
    string.valid[List[String]].ensure(List("Blank!"))(!_.trim.isEmpty).toEither

  def nonNegative(int: Int): Either[List[String], Int] =
    int.valid[List[String]].ensure(List("Negative!"))(_ >= 0).toEither

  val goodForm = Map("name" -> "Frank", "age" -> "12")
  val badForm = Map("name" -> "", "age" -> "twelve")

  println(readName(goodForm))
  println(readForm(badForm))
}
