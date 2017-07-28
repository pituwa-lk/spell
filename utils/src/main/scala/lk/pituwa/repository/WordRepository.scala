package lk.pituwa.repository

import com.typesafe.scalalogging.Logger
import lk.pituwa.adapter.H2Adapter

/**
  * Created by nayana on 27/7/17.
  */
object WordRepository
{
  import lk.pituwa.adapter.Implicits._
  ////0d80 3456 - 0DFF 3583
  val logger = Logger("words")

  val re = "[A-Za-z0-9\\\\(\\)]+".r

  val sinhala = "^[\\u0D80-\\u0DFF\\u200D]+$".r

  var words: Map[String, Int] = Map()

  lazy val boot = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM WORD")
    rs.toStream.map(v => v.getString("word") -> v.getInt("count")).toMap
  }

  def add(newWords: List[String]) = {
    logger.info("adding word to dictionary {}", words.size)
    newWords.filter(_.matches("""^[\u0D80-\u0DFF\u200D]+$""")).foreach(word => {
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
      val rs = db.select(s"SELECT word FROM WORD WHERE word = '${v._1}'")
      rs.toStream.size match {
        case 0 => db.execute(s"""INSERT INTO WORD (word, count) VALUES ('${v._1}', '${v._2}')""")
        case _ => logger.debug(s"${v} is already in db")
      }

    })
  }
}
