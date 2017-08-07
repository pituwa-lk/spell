package lk.pituwa.service

import org.scalatest.FlatSpec

/**
  * Created by nayana on 8/7/17.
  */
class WordServiceSpec extends FlatSpec
{
  "WordService" should "return a list of closely matching words for levenstine" in {
    val list = WordService.levenshteinMap("ආද")
    assert(list._2.nonEmpty)
  }
}
