package monads

import cats.{Id, Monad}
import scala.language.higherKinds


object MonadInstances {
  implicit val idMonad = new MyMonad[Id] {
    override def pure[A](a: A): Id[A] = a

    override def flatMap[A, B](fa: Id[A])(f: (A) => Id[B]): Id[B] = f(fa)

    override def map[A, B](fa: Id[A])(f: (A) => B): Id[B] = f(fa)
  }
}

object MonadApp extends App {
  import cats.Monad

  def optionMonads: Unit = {
    import cats.instances.option._

    val opt1 = Monad[Option].pure(3)
    val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))
    val opt3 = Monad[Option].map(opt1)(a => 100 * a)

    println("opt1 = " + opt1)
    println("opt2 = " + opt2)
    println("opt3 = " + opt3)
  }

  def listMonads: Unit = {
    import cats.instances.list._

    val list1 = Monad[List].pure(3)
    val list2 = Monad[List].flatMap(List(1, 2, 3))(x => List(x, x * 10))
    val list3 = Monad[List].map(list2)(_ + 123)

    println("list1 = " + list1)
    println("list2 = " + list2)
    println("list3 = " + list3)
  }

  def futureMonads: Unit = {
    import cats.instances.future._
    import scala.concurrent._
    import scala.concurrent.duration._
    import scala.concurrent.ExecutionContext.Implicits.global

    val fm = Monad[Future]

    val future = Await.result(
      fm.flatMap(fm.pure(1)) { x =>
        fm.pure(x + 2)
      },
      1.second
    )

    println("future = " + future)
  }

  def customMonads: Unit = {
    import cats.syntax.functor._
    import cats.syntax.flatMap._
    import cats.syntax.option._
    import cats.instances.option._
    import cats.instances.list._

    def sumSquare[M[_]: Monad](a: M[Int], b: M[Int]): M[Int] = {
      //a.flatMap(x => b.map(y => x * x + y * y))
      for {
        x <- a
        y <- b
      } yield x * x + y * y
    }

    val optionSumSquare = sumSquare(3.some, 4.some)
    val listSumSquare = sumSquare(List(1, 2, 3), List(4, 5))

    println("option sum square = " + optionSumSquare)
    println("list sum square = " + listSumSquare)
  }

  def idMonad: Unit = {
    import cats.Id
    import cats.syntax.flatMap._
    import cats.syntax.functor._

    val a = Monad[Id].pure(3)
    val b = Monad[Id].flatMap(a)(_ + 1)
    val c = for {
      x <- a
      y <- b
    } yield x + y

    println("a = " + a)
    println("b = " + b)
    println("c = " + c)
  }

  optionMonads
  println
  listMonads
  println
  futureMonads
  println
  customMonads
  println
  idMonad
}
