/**
  * Created by nayana on 28/7/17.
  */
case class Word(word: String) {

  var x = 0

  lazy val bytes = {
    val rng = (0 until word.length).toList
        rng.map(v => word.codePointAt(v)).mkString("-")
  }


  lazy val ct = "" //word.codePointCount(0, 1)
}

object WordTest extends App
{
  override def main(args: Array[String]) = {

    val x1 = "aඅර්‍ොනටරැඩනැචර"

    val x = x1.matches("""^[\u0D80-\u0DFF\u200D]+$""")

    println("fooo")


  }
}
//0d80 3456 - 0DFF 3583