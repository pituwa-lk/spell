package lk.pituwa.repository

import com.typesafe.scalalogging.Logger
import lk.pituwa.adapter.H2Adapter
import lk.pituwa.model.Link

import scala.collection.immutable
import scala.concurrent.Future


/**
  * Created by nayana on 27/7/17.
  */
object LinkRepository
{
  import lk.pituwa.adapter.Implicits._
  import scala.concurrent.ExecutionContext.Implicits.global

  val logger = Logger("links")

  var links: List[String] = List()

  var crawled: List[String] = List()

  def setCrawled(url: String): Unit = {
    saveOne(url)
    crawled = crawled ++ List(url)
  }

  var service = 0

  def get: String = {
    val ret = links.head
    links = links.tail
    ret
  }

  def getFilter(filter: String):String = {
      links.filter(s => s.contains(filter)).head
  }

  def empty: Boolean = links.isEmpty

  def bulkAdd(list: List[String]):Unit = {
    val delta = list.par.intersect(crawled.par)
    val non = list.par.diff(delta.par).toList
    links = links ::: non
    links = links.par.distinct.toList
    logger.info("report current link size: {} and visited {}", links.size, crawled.size)
  }

  /*def getById(id: Int):Option[String] = {
    links.find(v => v.id == id) match {
      case None => crawled.find(v => v.id == id)
      case Some(e) => Some(e)
    }
  }*/

  def init = {
    val db = new H2Adapter
    db.execute("CREATE TABLE IF NOT EXISTS LINKS(LINK CHAR(2000) NOT NULL,VISITED INT, ID INT DEFAULT NOT NULL AUTO_INCREMENT PRIMARY KEY)")
  }

  def saveOne(link : String): Unit = {
    Future {
      val db = new H2Adapter
      if (!isInDb(link)) db.execute(s"""INSERT INTO LINKS (link, visited) VALUES ('${sanitize(link)}', '1')""")
      else db.execute(s"""UPDATE LINKS SET VISITED = '1' WHERE LINK = '${sanitize(link)}'""")
    }
  }

  def save = {
    Future {
      links.par.foreach(v => {
        val db = new H2Adapter
        if (!isInDb(v)) {
          db.execute(s"""INSERT INTO LINKS (link, visited) VALUES ('${sanitize(v)}', '0')""")
          /*val rid = db.select("""CALL SCOPE_IDENTITY()""")
          var id = rid.toStream.head.getInt(0)*/
        }
      })
      crawled.par.foreach(v => {
        val db = new H2Adapter
        if (!isInDb(v)) db.execute(s"""INSERT INTO LINKS (link, visited) VALUES ('${sanitize(v)}', '1')""")
        else db.execute(s"""UPDATE LINKS SET VISITED = '1' WHERE LINK = '${sanitize(v)}'""")
      })
    }
  }

  import lk.pituwa.adapter.Implicits._

  lazy val boot = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM LINKS")
    rs.toStream.map(v => {
      v.getString("LINK") -> v.getInt("VISITED")
    }).toList.groupBy(_._2)
  }

  lazy val nonVisited: immutable.Iterable[String] = boot.filter(p => p._1 == 0).map(v => v._2.head._1)
  lazy val visited: immutable.Iterable[String] = boot.filter(p => p._1 == 1).map(v => v._2.head._1)

  def isInDb(url: String):Boolean = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM LINKS WHERE LINK = '${sanitize(url)}'")
    rs.toStream.nonEmpty
  }
  
  def sanitize(v: String):String = v.replaceAll("'", "&#39;")
}

