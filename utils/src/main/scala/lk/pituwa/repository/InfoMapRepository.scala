package lk.pituwa.repository

import lk.pituwa.adapter.H2Adapter
import lk.pituwa.model.{InfoMap, Link, Word}
import lk.pituwa.repository.LinkRepository.{crawled, isInDb, links, sanitize}

import scala.concurrent.Future

/**
  * Created by nayana on 30/7/17.
  */
object InfoMapRepository
{
  import lk.pituwa.adapter.Implicits._
  import scala.concurrent.ExecutionContext.Implicits.global

    var infoMaps: List[InfoMap] = List[InfoMap]()

    /*lazy val boot = {
      val db = new H2Adapter
      val rs = db.select(s"SELECT * FROM MAP")
      rs.toStream.map(v => {
        InfoMap(link = LinkRepository.getById(v.getInt("LINK_ID")), word = WordRepository.getById(v.getInt("WORD_ID")))
      })
    }*/

    def init = {
      val db = new H2Adapter
      db.execute("""CREATE TABLE IF NOT EXISTS MAP(
                   |    WORD_ID INT NOT NULL,
                   |    LINK_ID INT NOT NULL,
                   |    CREATED DATETIME,
                   |    PRIMARY KEY(WORD_ID, LINK_ID)
                   |);""".stripMargin)
    }

  /*def add(linfoMap: InfoMap): Unit = {
    val wordId = linfoMap.word.get.id match {
      case 0 => WordRepository.getByName(linfoMap.word.get.word).id
      case _ => linfoMap.word.get.id
    }
    infoMaps ++= List(linfoMap)
    Future {
      val db = new H2Adapter
      if (!isInDb(linfoMap)) db.execute(s"""INSERT INTO MAP (WORD_ID, LINK_ID) VALUES ('${wordId}', '${linfoMap.link.get.id}')""")
    }
  }*/

  def isInDb(infoMap: InfoMap):Boolean = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM MAP WHERE WORD_ID = '${infoMap.word.get.id}' AND LINK_ID = '${infoMap.link.get.id}'")
    rs.toStream.nonEmpty
  }
}
