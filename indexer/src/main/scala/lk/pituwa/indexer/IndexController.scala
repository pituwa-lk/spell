package lk.pituwa.indexer

import lk.pituwa.model.Document
import lk.pituwa.repository.WordRepository

import scala.concurrent.Future

/**
  * Created by Nayana on 27/7/17.
  */
class IndexController
{
  def accept(document: Document):Future[Boolean] = {
    Future {
        //document.texts.isEmpty
      document.texts.map(sentence => {
        sentence.split(" ").map(word => {
          WordRepository.add(word)
        })
      })
    }
  }
}
