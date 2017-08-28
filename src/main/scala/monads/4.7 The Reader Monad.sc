import cats.data.Reader

case class Cat(name: String, favouriteFood: String)

val catName: Reader[Cat, String] = Reader(cat => cat.name)

catName.run(Cat("Garfield", "lasagne"))

val greetKitty: Reader[Cat, String] = catName.map(name => s"Hello $name")

greetKitty.run(Cat("Heathcliff", "junk food"))

val feedKitty: Reader[Cat, String] = Reader(cat => s"Have a nice bowl of ${cat.favouriteFood}")

val greetAndFeed: Reader[Cat, String] =
  for {
    msg1 <- greetKitty
    msg2 <- feedKitty
  } yield s"$msg1 $msg2"

greetAndFeed(Cat("Garfield", "lasagne"))

greetAndFeed(Cat("Heathcliff", "junk food"))
