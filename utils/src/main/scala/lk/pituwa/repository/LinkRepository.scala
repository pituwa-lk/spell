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

  var links: List[Link] = List()

  var crawled: List[Link] = List()

  def setCrawled(url: Link): Unit = {
    saveOne(url)
    crawled = crawled ++ List(url)
  }

  var service = 0

  def get: Link = {
    val ret = links.head
    links = links.tail
    ret
  }

  def getFilter(filter: String):Link = {
      links.filter(s => s.link.contains(filter)).head
  }

  def empty: Boolean = links.isEmpty

  def bulkAdd(list: List[Link]):Unit = {
    val delta = list.par.intersect(crawled.par)
    val non = list.par.diff(delta.par).toList
    links = links ::: non
    links = links.par.distinct.toList
    logger.info("report current link size: {} and visited {}", links.size, crawled.size)
  }

  def getById(id: Int):Option[Link] = {
    links.find(v => v.id == id) match {
      case None => crawled.find(v => v.id == id)
      case Some(e) => Some(e)
    }
  }

  def init = {
    val db = new H2Adapter
    db.execute("CREATE TABLE IF NOT EXISTS LINKS(LINK CHAR(2000) NOT NULL,VISITED INT, ID INT DEFAULT NOT NULL AUTO_INCREMENT PRIMARY KEY)")
  }

  def saveOne(link : Link): Unit = {
    Future {
      val db = new H2Adapter
      if (!isInDb(link)) db.execute(s"""INSERT INTO LINKS (link, visited) VALUES ('${sanitize(link.link)}', '1')""")
      else db.execute(s"""UPDATE LINKS SET VISITED = '1' WHERE LINK = '${sanitize(link.link)}'""")
    }
  }

  def save = {
    Future {
      links.par.foreach(v => {
        val db = new H2Adapter
        if (!isInDb(v)) {
          db.execute(s"""INSERT INTO LINKS (link, visited) VALUES ('${sanitize(v.link)}', '0')""")
          val rid = db.select("""CALL SCOPE_IDENTITY()""")
          v.id = rid.toStream.head.getInt(0)
        }
      })
      crawled.par.foreach(v => {
        val db = new H2Adapter
        if (!isInDb(v)) db.execute(s"""INSERT INTO LINKS (link, visited) VALUES ('${sanitize(v.link)}', '1')""")
        else db.execute(s"""UPDATE LINKS SET VISITED = '1' WHERE LINK = '${sanitize(v.link)}'""")
      })
    }
  }

  import lk.pituwa.adapter.Implicits._

  lazy val boot = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM LINKS")
    rs.toStream.map(v => {
      Link(link = v.getString("LINK"), visited = v.getInt("VISITED"), id = v.getInt("ID"))
    }).toList.groupBy(_.visited)
  }

  lazy val nonVisited: immutable.Iterable[Link] = boot.filter(p => p._1 == 0).flatMap(v => v._2)
  lazy val visited: immutable.Iterable[Link] = boot.filter(p => p._1 == 1).flatMap(v => v._2)

  def isInDb(url: Link):Boolean = {
    val db = new H2Adapter
    val rs = db.select(s"SELECT * FROM LINKS WHERE LINK = '${sanitize(url.link)}'")
    rs.toStream.nonEmpty
  }
  
  def sanitize(v: String):String = v.replaceAll("'", "&#39;")
}

