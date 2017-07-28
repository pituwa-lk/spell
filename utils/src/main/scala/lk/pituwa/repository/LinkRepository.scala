package lk.pituwa.repository

import com.typesafe.scalalogging.Logger
import lk.pituwa.adapter.H2Adapter
import lk.pituwa.repository.WordRepository.words


/**
  * Created by nayana on 27/7/17.
  */
object LinkRepository
{
  val logger = Logger("links")

  var links: List[String] = List()

  var crawled: List[String] = List()

  def setCrawled(url: String): Unit = {
    crawled = crawled ++ List(url)
  }

  def get: String = {
    val ret = links.head
    links = links.tail
    ret
  }

  def empty: Boolean = links.isEmpty

  def bulkAdd(list: List[String]):Unit = {
    val delta = list.intersect(crawled)
    val non = list.diff(delta)
    links = links ::: non
    links = links.distinct
    logger.info("report current link size: {} and visited {}", links.size, crawled.size)
  }

  def init = {
    val db = new H2Adapter
    db.execute("CREATE TABLE IF NOT EXISTS LINKS (link CHAR(200) PRIMARY KEY, visited INT)")
  }

  def save = {
    links.foreach(v => {
      val db = new H2Adapter
      db.execute(s"""INSERT INTO LINK (link, visited) VALUES ("$v", 0)""")
    })
    crawled.foreach(v => {
      val db = new H2Adapter
      db.execute(s"""INSERT INTO LINK (link, visited) VALUES ("$v", 1)""")
    })
  }
}
