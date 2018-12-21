import cats.data._
import cats.implicits._

import models.User

def readName(form: Map[String, String]): Either[List[String], String] = ???

def readAge(form: Map[String, String]): Either[List[String], Int] = ???
