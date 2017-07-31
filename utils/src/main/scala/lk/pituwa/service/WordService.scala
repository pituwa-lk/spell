package lk.pituwa.service



import com.rockymadden.stringmetric.similarity.JaroWinklerMetric
import lk.pituwa.repository.WordRepository

import scala.concurrent.Future

/**
  * Created by nayana on 28/7/17.
  */
object WordService {

  import scala.concurrent.ExecutionContext.Implicits.global


  def bulkLookup(words: List[String]):Future[Map[String,List[String]]] = {
    Future.successful {
      //words.map(word => word -> WordRepository.getByPrefixName(word)).toMap
      words.map(
        word => word -> WordRepository.similar(word).sortBy(p2 => JaroWinklerMetric.compare(word, p2).get)
      ).toMap
    }
  }

  //we can add a loop and return words based on min max criteria
  def getWordWithPrefix(prefix: String, delta: Int = 0):Future[List[String]] = {
    Future {
      val words = WordRepository.words.keys.toList.par
      words.filter(word => { score(word, prefix) == (prefix.length - delta) } ).toList.slice(0,10)
    }
  }

  def score(p1: String, p2: String, matched: Int = 0):Int = {
    if (p1.length == 0 || p2.length == 0) matched
    else {
      p1.charAt(0) == p2.charAt(0) match {
        case true =>
          if (p1.length > 1 && p2.length > 1) {
            score(p1.substring(1), p2.substring(1), matched + 1)
          } else {
            matched + 1
          }
        case false => matched
      }
    }
  }
}
