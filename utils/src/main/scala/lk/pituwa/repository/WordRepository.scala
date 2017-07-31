package lk.pituwa.repository

import com.rockymadden.stringmetric.similarity.JaroWinklerMetric
import com.typesafe.scalalogging.Logger
import lk.pituwa.adapter.H2Adapter
import lk.pituwa.model.Word

import scala.concurrent.Future

/**
  * Created by nayana on 27/7/17.
  */
object WordRepository
{
  import lk.pituwa.adapter.Implicits._
  import scala.concurrent.ExecutionContext.Implicits.global
  ////0d80 3456 - 0DFF 3583
  val logger = Logger("words")

  val re = "[A-Za-z0-9\\\\(\\)]+".r

  val sinhala = "^[\\u0D80-\\u0DFF\\u200D]+$".r

  var words: Map[String, Word] = Map()

  //TODO: have some test data
  lazy val boot = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM WORD")
    rs.toStream.map(v => { v.getString("WORD") -> Word(v.getString("WORD"), v.getInt("ID"), v.getInt("COUNT"))}).toMap
  }

  def similar(p1: String):List[String] = {
      words.keys.filter(p2 => {
        JaroWinklerMetric.compare(p1, p2) match {
          case None => false
          case Some(e) => if (e > 0.9) true else false
        }
      }).toList
  }

  def add(filtered: List[Word]):Unit = {
    logger.info("adding word to dictionary {}", words.size)
    //val filtered = newWords.filter(_.word.matches("""^[\u0D80-\u0DFF\u200D]+$"""))
    //val trash = newWords.filter(_.word.matches("""^[^\u0D80-\u0DFF\u200D]+$"""))
    logger.info("size after filter range {}", filtered.size)

    filtered.map(v => {
      v.count += 1
      words = words.updated(v.word, v)
      Future {
        isInDb(v) match {
          case true => {
            v
          }
          case false => {
            saveOne(v).map(v => {
              v
            })
          }
        }
        v
      }
    })
  }

  def init = {
    val db = new H2Adapter
    db.execute("CREATE TABLE IF NOT EXISTS WORD (WORD CHAR(100) NOT NULL, COUNT INT, ID INT DEFAULT NOT NULL PRIMARY KEY AUTO_INCREMENT)")
    db.execute("CREATE TABLE IF NOT EXISTS TRASH (WORD CHAR(100) NOT NULL, COUNT INT, ID INT DEFAULT NOT NULL PRIMARY KEY AUTO_INCREMENT)")
  }

  def isInDb(word: Word): Boolean = {
    val db = new H2Adapter
    db.select(s"""SELECT ID FROM WORD WHERE WORD = '${word.word}' """).toStream.nonEmpty
  }

  def getByPrefixName(prefix: String):List[String] = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT WORD FROM WORD WHERE WORD LIKE '${prefix}%' LIMIT 10").toStream
    rs.map(r => {
      r.getString("WORD")
    }).toList
  }

  def getStats(wd: String):List[String] = {
    //SELECT SUBSTR(WORD, 0, 1) AS G, COUNT(*) AS cnt  FROM WORD  GROUP BY G HAVING cnt > 1000 ORDER BY cnt DESC LIMIT 10
    List("aaa")
  }

  def getByName(word: String):Word = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT WORD, ID, COUNT FROM WORD WHERE WORD = '${word}'").toStream.head
    Word(rs.getString("WORD"), rs.getInt("ID"), rs.getInt("COUNT"))
  }

  def getById(id : Int):Option[Word] = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT word FROM WORD WHERE ID = '${id}'").toStream.headOption
    rs match {
      case None => None
      case _ => {
        val word = rs.get.getString("WORD")
        words.get(word)
      }
    }
  }

  def saveTrash(word: Word): Word = {
    val db = new H2Adapter
    if (word.id == 0) {
      word.id = db.execute(s"""INSERT INTO TRASH (WORD, COUNT) VALUES ('${word.word}', 1)""").toInt
    }
    word
  }

  def saveOne(word: Word): Future[Word] = Future{
    val db = new H2Adapter
    if (word.id == 0 && !isInDb(word)) {
        word.id = db.execute(s"""INSERT INTO WORD (WORD, COUNT) VALUES ('${word.word}', 1)""").toInt
    }
    word
  }

  def save = Future {
    words.foreach(v => {
      val db = new H2Adapter
      val rs = db.select(s"SELECT word FROM WORD WHERE word = '${v._1}'")
      rs.toStream.size match {
        case 0 => {
          v._2.id =  db.execute(s"""INSERT INTO WORD (word, count) VALUES ('${v._1}', '${v._2.count}')""").toInt
        }
        case _ => db.execute("SELECT 1")
      }

    })
  }
}
