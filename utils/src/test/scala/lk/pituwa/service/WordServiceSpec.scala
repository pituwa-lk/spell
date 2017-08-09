package lk.pituwa.service

import org.scalatest.FlatSpec

import scala.concurrent._
import scala.concurrent.duration.Duration

/**
  * Created by nayana on 8/7/17.
  */
class WordServiceSpec extends FlatSpec
{

  "WordService" should "provide statistics on index " in {
    val stats = WordService.alphaStats
    assert(stats.slice(0, 10).nonEmpty)
  }

  "WordService" should "have a workable tree" in {
    assert(WordService.wordTree.nonEmpty)
    assert(WordService.wordTree.isDefinedAt("ලා"))
    /*val keys = WordService.wordTree.keys.slice(0, 10)
    assert(keys.nonEmpty)*/
  }

  "WordService" should "return a list of closely matching words for levenstine" in {
    val list = WordService.levenshteinMap("ආද")
    assert(list.nonEmpty)
    assert(list.size == 10)
  }

  "WordService" should "return a map when spell checked with a string" in {
    val document = "ආයුෙබොවන් ගෙරද යන්න එන්න කද"
    val ret = WordService.spellCheck(document)

    val map = Await.result(ret, Duration.Inf)
    assert(map.keys.size > 0)
  }
}
