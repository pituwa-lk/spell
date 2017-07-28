package lk.pituwa.repository

import com.typesafe.scalalogging.Logger
import lk.pituwa.adapter.H2Adapter

/**
  * Created by nayana on 27/7/17.
  */
object WordRepository
{
  val logger = Logger("words")
  val re = "[A-Za-z0-9]+".r
  var words: Map[String, Int] = Map()

  def add(newWords: List[String]) = {
    logger.info("adding word to dictionary {}", words.size)
    newWords.filter(v => re.findFirstIn(v) match {
      case Some(e) => false
      case None => true
    }).map(v => v.replaceAll('"'.toString, "")).filter(x => x.trim != "").foreach(word => {
      words.get(word) match {
        case None => words += (word -> 1)
        case Some(v) => words.updated(word, v + 1)
      }
    })
  }

  def init = {
    val db = new H2Adapter
    db.execute("CREATE TABLE IF NOT EXISTS WORD (word CHAR(100) PRIMARY KEY, count INT)")
  }

  def save = {
    words.foreach(v => {
      val db = new H2Adapter
      db.execute(s"""INSERT INTO WORD (word, count) VALUES (${v._1}, ${v._2})""")
    })
  }
}
