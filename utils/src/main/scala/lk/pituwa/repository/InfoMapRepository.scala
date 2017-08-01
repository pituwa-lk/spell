package lk.pituwa.repository

import lk.pituwa.adapter.H2Adapter

/**
  * Created by nayana on 30/7/17.
  */
object InfoMapRepository
{
  import lk.pituwa.adapter.Implicits._

    def init = {
      val db = new H2Adapter
      db.execute("""CREATE TABLE IF NOT EXISTS WORD_DOMAIN(
                   |    WORD CHAR(100) NOT NULL,
                   |    DOMAIN CHAR(100) NOT NULL,
                   |    CREATED TIMESTAMP,
                   |    ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY
                   |);""".stripMargin)
    }

  def add(word: String, domain: String): Unit = {
      val db = new H2Adapter
      if (!isInDb(word, domain)) {
        db.execute(s"""INSERT INTO WORD_DOMAIN (WORD, DOMAIN) VALUES ('$word', '$domain')""")
      }
  }

  def isInDb(word: String, domain: String):Boolean = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM WORD_DOMAIN WHERE WORD = '$word' AND DOMAIN = '$domain'")
    rs.toStream.nonEmpty
  }
}
