package lk.pituwa.service



import lk.pituwa.repository.WordRepository

import scala.concurrent.Future

/**
  * Created by nayana on 28/7/17.
  */
object WordService {

  //we can add a loop and return words based on min max criteria
  def getWordWithPrefix(prefix: String):Future[List[String]] = {
    Future {
      val words: List[String] = WordRepository.words.keys.toList
      val base = words.filter(z => z.charAt(0) == prefix.charAt(0))
      val base2 = base.filter(z => z.charAt(1) == prefix.charAt(1))
      base2
    }
  }
}
