package functors

object Invariance extends App {
  def useSymbolSemigroup: Unit = {
//    import cats.Semigroup
//    import cats.instances.string._
//    import cats.syntax.invariant._
//
//    // imap is not showing up here. ¯\_(ツ)_/¯
//    implicit val symbolSemigroup = Semigroup[Symbol] = Semigroup[String].imap(Symbol.apply)(_.name)
//
//    import cats.syntax.semigroup._
//
//    'a |+| 'few |+| 'words
  }
}
