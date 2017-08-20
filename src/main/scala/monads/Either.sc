import cats.syntax.either._

import scala.util.Try

Right(123).flatMap(x => Right(x * 2))

val a = 3.asRight
val b = 4.asRight

for {
  x <- a
  y <- b
} yield x*x + y*y

def countPositive(nums: List[Int]) = {
  nums.foldLeft(0.asRight[String]) { (accumulator, num) =>
    if (num > 0) {
      accumulator.map(_ + 1)
    }
    else {
      Left("Negative. Stopping!")
    }
  }
}

countPositive(List(1, 2, 3))
countPositive(List(1, -2, 3))

Either.catchOnly[NumberFormatException]("foo".toInt)
Either.catchNonFatal(sys.error("Badness"))

Either.fromTry(Try("foo".toInt))
Either.fromOption[String, Int](None, "Badness")

"Error".asLeft[Int].getOrElse(0)
"Error".asLeft[Int].orElse(2.asRight[String])

(-1).asRight[String].ensure("Must be non-negative!")(_ > 0)

"error".asLeft[String] recover {
  case str: String => "Recovered from " + str
}

"error".asLeft[String] recoverWith {
  case str: String => Right("Recovered from " + str)
}

"foo".asLeft[Int].leftMap(_.reverse)

6.asRight[String].bimap(_.reverse, _ * 7)

"bar".asLeft[Int].bimap(_.reverse, _ * 7)

123.asRight[String]
123.asRight[String].swap


for {
  a <- 1.asRight[String]
  b <- 0.asRight[String]
  c <- if(b == 0) "DIV0".asLeft[Int] else (a / b).asRight[String]
} yield c * 100

