import cats.data._
import cats.implicits._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

type Response[A] = EitherT[Future, String, A]

val powerLevels = Map(
  "Jazz"      -> 6,
  "Bumblebee" -> 8,
  "Hot Rod"   -> 10
)

def getPowerLevel(autobot: String): Response[Int] = {
  powerLevels.get(autobot) match {
    case Some(level) => EitherT.right(Future(level))
    case None => EitherT.left(Future(s"$autobot unreachable"))
  }
}

def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = for {
  powerLevel1 <- getPowerLevel(ally1)
  powerLevel2 <- getPowerLevel(ally2)
} yield (powerLevel1 + powerLevel2) > 15

def tacticalReport(ally1: String, ally2: String): String = {
  Await.result(canSpecialMove(ally1, ally2).value, 1.second) match {
    case Left(message) => s"Comms error: $message"
    case Right(true) => s"$ally1 and $ally2 are ready to roll out!"
    case Right(false) => s"$ally1 and $ally2 need a recharge."
  }
}

tacticalReport("Jazz", "Bumblebee")

tacticalReport("Bumblebee", "Hot Rod")

tacticalReport("Jazz", "Ironhide")
