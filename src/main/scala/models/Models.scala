package models

final case class Person(name: String, email: String)

final case class Cat(name: String, age: Int, color: String)

final case class Order(totalCost: Double, quantity: Double)

final case class Box[A](value: A)

case class User(name: String, age: Int)
