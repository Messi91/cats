import cats.data.Writer
import cats.instances.vector._
import cats.syntax.applicative._
import cats.syntax.writer._

type Logged[A] = Writer[Vector[String], A]

val writer1 = for {
  a <- 10.pure[Logged]
  _ <- Vector("a", "b", "c").tell
  b <- 32.writer(Vector("x", "y", "z"))
} yield a + b

writer1.run


val writer2 = writer1.mapWritten(_.map(_.toUpperCase))

writer2.run


val writer3 = writer1.bimap(
  log => log.map(_.toUpperCase),
  result => result * 100
)

writer3.run


val writer4 = writer1.mapBoth((log, result) => (
  log.map(_.toUpperCase),
  result * 100
))

writer4.run


val writer5 = writer1.reset

writer5.run


val writer6 = writer1.swap

writer6.run