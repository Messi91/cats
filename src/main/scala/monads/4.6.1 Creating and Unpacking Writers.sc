import cats.data.Writer
import cats.instances.vector._

Writer(Vector(
  "It was the best of times",
  "It was the worst of times"
), 123)


import cats.syntax.applicative._

type Logged[A] = Writer[Vector[String], A]

123.pure[Logged]


import cats.syntax.writer._

Vector("msg1", "msg2", "msg3").tell


val a = Writer(Vector("msg1", "msg2", "msg3"), 123)

val b = 123.writer(Vector("msg1", "msg2", "msg3"))

a.value

a.written

val (log, result) = b.run
log
result