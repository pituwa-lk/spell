package lk.pituwa.repository

/**
  * Created by nayana on 27/7/17.
  */
object WordRepository
{
    var words: Map[String, Int] = Map()

    def add(word: String) = {
      words.get(word) match {
        case None => words += (word -> 1)
        case Some(v) => words(word) += 1
      }
    }
}
