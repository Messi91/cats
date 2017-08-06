package typeclasses

import models.Person

/*
  - There are three important components to the type class pattern:
	  - the type class itself
	  - instances for particular types
	  - the interface methods that we expose to users
*/

// Type class
sealed trait Json

final case class JsObject(get: Map[String, Json]) extends Json

final case class JsString(get: String) extends Json

final case class JsNumber(get: Double) extends Json

trait JsonWriter[A] {
  def write(value: A): Json
}


// Instances
object JsonWriterInstances {
  implicit val stringJsonWriter = new JsonWriter[String] {
    def write(value: String): Json =
      JsString(value)
  }

  implicit val personJsonWriter = new JsonWriter[Person] {
    def write(value: Person): Json =
      JsObject(Map(
        "name" -> JsString(value.name),
        "email" -> JsString(value.email)
      ))
  }
}


// Interfaces
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}

object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json =
      w.write(value)
  }
}


// Usage
object JsonApp extends App {

  def useSingleton: Unit = {
    import JsonWriterInstances._
    val json = Json.toJson(Person("Dave", "dave@example.com"))
    println("singleton = " + json)
  }

  def useExtension: Unit = {
    import JsonWriterInstances._
    import JsonSyntax._
    val json = Person("Dave", "dave@example.com").toJson
    println("extension = " + json)
  }

  useSingleton
  useExtension
}
