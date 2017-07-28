package lk.pituwa.repository

import com.typesafe.scalalogging.Logger
import lk.pituwa.adapter.H2Adapter


import scala.concurrent.Future


/**
  * Created by nayana on 27/7/17.
  */
object LinkRepository
{

  import scala.concurrent.ExecutionContext.Implicits.global

  val logger = Logger("links")

  var links: List[String] = List()

  var crawled: List[String] = List()

  def setCrawled(url: String): Unit = {
    crawled = crawled ++ List(url)
  }

  var service = 0

  def get: String = {
    val ret = links.head
    links = links.tail
    ret
  }

  def empty: Boolean = links.isEmpty

  def bulkAdd(list: List[String]):Unit = {
    val delta = list.par.intersect(crawled.par)
    val non = list.par.diff(delta.par).toList
    links = links ::: non
    links = links.par.distinct.toList
    logger.info("report current link size: {} and visited {}", links.size, crawled.size)
  }

  def init = {
    val db = new H2Adapter
    db.execute("CREATE TABLE IF NOT EXISTS LINKS (link CHAR(200) PRIMARY KEY, visited INT)")
  }

  def save = {
    Future {
      links.par.foreach(v => {
        val db = new H2Adapter
        if (!isInDb(v)) db.execute(s"""INSERT INTO LINKS (link, visited) VALUES ('$v', '0')""")
      })
      crawled.par.foreach(v => {
        val db = new H2Adapter
        if (!isInDb(v)) db.execute(s"""INSERT INTO LINKS (link, visited) VALUES ('$v', '1')""")
      })
    }
  }

  import lk.pituwa.adapter.Implicits._

  lazy val boot = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM LINKS")
    rs.toStream.map(v => v.getString("LINK") -> v.getInt("VISITED")).toList.groupBy(_._2)
  }

  lazy val nonB = boot.filter(p => p._1 == 0).flatMap(p => p._2.map(v => v._1))
  lazy val vis  = boot.filter(p => p._1 == 1).flatMap(p => p._2.map(v => v._1))

  def isInDb(url: String):Boolean = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM LINKS WHERE LINK = '${url}'")
    rs.toStream.nonEmpty
  }
}

