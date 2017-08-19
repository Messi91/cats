package functors

object Contravariance extends App {
  def useSymbol: Unit = {
    import cats.Show
    import cats.functor.Contravariant
    import cats.instances.string._

    val showString = Show[String]
    val showSymbol: Show[Symbol] = Contravariant[Show].contramap(showString)((symbol: Symbol) => s"'${symbol.name}")
    val showDave = showSymbol.show('dave)

    println(s"Symbol = $showDave")
  }

  def useSyntax: Unit = {
    import cats.instances.function._
    import cats.syntax.contravariant._

    val div: Int => Double = _ / 2.0
    val add: Int => Int    = _ + 1
    val result = div.compose(add)(2) // contramap is not showing up here. ¯\_(ツ)_/¯

    println(s"Double = $result")
  }

  useSymbol
  println
  useSyntax
}
