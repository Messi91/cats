import cats.Eval

val greeting = Eval.always {
  println("Step 1")
  "Hello"
}.map { str =>
  println("Step 2")
  str + " world"
}

greeting.value


val ans = for {
  a <- Eval.now { println("Calculating A"); 40 }
  b <- Eval.always { println("Calculating B"); 2 }
} yield {
  println("Adding A and B")
  a + b
}
ans.value

ans.value


val saying = Eval.always {
  println("Step 1")
  "The cat"
}.map { str =>
  println("Step 2")
  s"$str sat on"
}.memoize.map { str =>
  println("Step 3")
  s"$str the mat"
}

saying.value

saying.value