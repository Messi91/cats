import cats.data.State
import cats.syntax.applicative._

type CalcState[A] = State[List[Int], A]

def evalOne(sym: String): CalcState[Int] = {
  State[List[Int], Int] { oldStack =>
    if(sym.toCharArray.head.isDigit) {
      val newStack = sym.toInt +: oldStack
      val result = sym.toInt
      (newStack, result)
    }
    else {
      val result = sym match {
        case "+" => oldStack.head + oldStack(1)
        case "-" => oldStack.head - oldStack(1)
        case "*" => oldStack.head * oldStack(1)
        case "/" => oldStack.head / oldStack(1)
      }
      val newStack = result +: oldStack.drop(2)
      (newStack, result)
    }
  }
}

evalOne("42").runA(Nil).value

val program1 = for {
  _ <- evalOne("1")
  _ <- evalOne("2")
  ans <- evalOne("+")
} yield ans

program1.runA(Nil).value

def evalAll(input: List[String]): CalcState[Int] = {
  input.foldLeft(0.pure[CalcState]){(acc, sym) =>
    acc.flatMap(_ => evalOne(sym))
  }
}

val program2 = evalAll(List("1", "2", "+", "3", "*"))

program2.runA(Nil).value
