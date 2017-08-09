package lk.pituwa.repository


import com.typesafe.scalalogging.Logger
import lk.pituwa.adapter._
import lk.pituwa.model.Word

import scala.concurrent.Future
import scala.util.matching.Regex

/**
  * Created by nayana on 27/7/17.
  */
object WordRepository
{
  import lk.pituwa.adapter.Implicits._
  ////0d80 3456 - 0DFF 3583
  val logger = Logger("word-tree")

  val re: Regex = "[A-Za-z0-9\\\\(\\)]+".r

  val sinhala: Regex = "^[\\u0D80-\\u0DFF\\u200D]+$".r

  def get = {
    val db = new H2Adapter
    val rs = db.select("SELECT * FROM WORD")
    val map = rs.toStream.map(v => v.getString("WORD")).toList
    map
  }

  def add(filtered: List[String]):Unit = {
    filtered.foreach(v => {
        saveOne(v)
    })
  }

  def init = {
    val db = new H2Adapter
    db.execute("CREATE TABLE IF NOT EXISTS WORD (WORD CHAR(100) NOT NULL, COUNT INT, ID INT DEFAULT NOT NULL PRIMARY KEY AUTO_INCREMENT)")
    db.execute("CREATE TABLE IF NOT EXISTS TRASH (WORD CHAR(100) NOT NULL, COUNT INT, ID INT DEFAULT NOT NULL PRIMARY KEY AUTO_INCREMENT)")
  }

  //this check can be done via the online index too
  def isInDb(word: String): Boolean = {
    val db = new H2Adapter
    db.select(s"""SELECT ID FROM WORD WHERE WORD = '${word}' """).toStream.nonEmpty
  }

  //this method is good too for suggestions
  def getByPrefixName(prefix: String):List[String] = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT WORD FROM WORD WHERE WORD LIKE '${prefix}%' LIMIT 10").toStream
    rs.map(r => {
      r.getString("WORD")
    }).toList
  }

  def getByName(word: String):String = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT WORD, ID, COUNT FROM WORD WHERE WORD = '${word}'").toStream.head
    rs.getString("WORD")
  }

  def saveOne(word: String): Unit = {
    val db = new H2Adapter
    if (!isInDb(word)) {
        db.execute(s"""INSERT INTO WORD (WORD, COUNT) VALUES ('${word}', 1)""")
    }
  }
}
