package functors

import models.Box

import scala.util.Try

trait Codec[A] {
  def encode(value: A): String
  def decode(value: String): Option[A]

  def imap[B](dec: A => B, enc: B => A): Codec[B] = {
    val self = this
    new Codec[B] {
      override def encode(value: B) = self.encode(enc(value))
      override def decode(value: String) = self.decode(value).map(dec)
    }
  }
}

object CodecInstances {
  implicit val intCodec = new Codec[Int] {
    override def encode(value: Int) = value.toString
    override def decode(value: String) = Try(value.toInt).toOption
  }

  implicit def boxCodec[A](implicit codec: Codec[A]): Codec[Box[A]] = {
    codec.imap[Box[A]](Box(_), _.value)
  }
}

object Codec {
  def encode[A](value: A)(implicit c: Codec[A]): String = c.encode(value)
  def decode[A](value: String)(implicit c: Codec[A]): Option[A] = c.decode(value)
}

object CodecApp extends App {
  import CodecInstances._

  def testInt: Unit = {
    val string = Codec.encode(12345)
    val integer = Codec.decode[Int]("12345")
    val wrong = Codec.decode[Int]("Cat on a hot tin roof")

    println("String = " + string)
    println("Integer = " + integer)
    println("wrong = " + wrong)
  }

  def testBox: Unit = {
    val string = Codec.encode(Box(12345))
    val integer = Codec.decode[Box[Int]]("12345")
    val wrong = Codec.decode[Box[Int]]("Cat on a hot tin roof")

    println("String = " + string)
    println("Integer = " + integer)
    println("wrong = " + wrong)
  }

  testInt
  println
  testBox
}
