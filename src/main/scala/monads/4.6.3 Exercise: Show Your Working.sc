import cats.data.Writer
import cats.instances.vector._
import cats.syntax.applicative._
import cats.syntax.writer._

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

def slowly[A](body: => A) = try body finally Thread.sleep(100)

def factorial(n: Int): Int = {
  val ans = slowly(if(n == 0) 1 else n * factorial(n - 1))
  println(s"fact $n $ans")
  ans
}

factorial(5)

//Await.result(Future.sequence(Vector(
//  Future(factorial(5)),
//  Future(factorial(5))
//)), 5.seconds)
//
//type Logged = Writer[Vector[String], Int]
//
//def factorial(logged: Logged): Logged = {
//  val ans = slowly {
//    if(logged.value == 0) 1.pure[Logged]
//    else factorial(logged.map(_ - 1)).map(_ * logged.value)
//  }
//  ans.writer(Vector(s"fact ${logged.value} $ans"))
//}