import cats.Applicative
import cats.data.Validated
import cats.implicits._
import cats.instances.list._
import cats.syntax.applicative._

import scala.language.higherKinds

def listTraverse[F[_]: Applicative, A, B](list: List[A])(func: A => F[B]): F[List[B]] =
  list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
    (accum, func(item)).mapN(_ :+ _)
  }

def listSequence[F[_]: Applicative, B](list: List[F[B]]): F[List[B]] =
  listTraverse(list)(identity)

def process(inputs: List[Int]) =
  listTraverse(inputs)(n => if (n % 2 == 0) Some(n) else None)

//def process(inputs: List[Int]) =
//  listTraverse(inputs) { n =>
//    if (n % 2 == 0)
//      Validated.valid(n)
//    else
//      Validated.invalid(List(s"$n is not even"))
//  }

listSequence(List(Vector(1, 2), Vector(3, 4)))
listSequence(List(Vector(1, 2), Vector(3, 4), Vector(5, 6)))

process(List(2, 4, 6))
process(List(1, 2, 3))
