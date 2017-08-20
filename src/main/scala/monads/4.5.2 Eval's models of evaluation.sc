import cats.Eval

val now = Eval.now(1 + 2)

val later = Eval.later(3 + 4)

val always = Eval.always(5 + 6)

now.value

later.value

always.value


val x = Eval.now {
  println("Computing X")
  1 + 1
}

x.value

x.value


val y = Eval.always {
  println("Computing Y")
  1 + 1
}

y.value

y.value


val z = Eval.later {
  println("Computing Z")
  1 + 1
}

z.value

z.value