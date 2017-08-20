import cats.Eval

//def factorial(n: BigInt): BigInt =
//  if(n == 1) n else n * factorial(n - 1)
//
//factorial(50000)


def factorial(n: BigInt): Eval[BigInt] =
  if(n == 1) Eval.now(n) else Eval.defer(factorial(n - 1)).map(_ * n)

factorial(50000).value


//def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = {
//  as match {
//    case head :: tail =>
//      fn(head, foldRight(tail, acc)(fn))
//    case Nil =>
//      acc
//  }
//}
//
//foldRight((1 to 10000).toList, 0)(_ + _)


def foldRight[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] = {
  as match {
    case head :: tail =>
      Eval.defer(fn(head, foldRight(tail, acc)(fn)))
    case Nil =>
      acc
  }
}

foldRight((1 to 1000000000).toList, Eval.now(0))((acc, item) => item.map(_ + acc)).value