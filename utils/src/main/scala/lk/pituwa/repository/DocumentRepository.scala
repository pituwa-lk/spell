package lk.pituwa.repository

import com.typesafe.scalalogging.Logger
import lk.pituwa.adapter.H2Adapter
import lk.pituwa.utils.FileOps

/**
  * Created by nayana on 27/7/17.
  */
object DocumentRepository
{
  ////0d80 3456 - 0DFF 3583
  val logger = Logger("documents")

  val drop = "proc"

  //URL + TEXT
  //var documents:List[String] = List[String]()

/*  val files: List[File]  = {
    val d = new File(drop)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }*/

/*  def processed(file: File): Unit = {
    import java.io.File
    import java.nio.file.{Files, StandardCopyOption}

    val hash = FileOps.computeHash(file.toPath.toString)
    val d1 = file.toPath
    val d2 = new File("archive/" + file.getName).toPath

    Files.move(d1, d2, StandardCopyOption.ATOMIC_MOVE)

    val db = new H2Adapter
    db.execute(s"""INSERT INTO DOCUMENT (DOCUMENT) VALUES ('${hash}')""")
    documents ++= List(hash)
  }*/

  import lk.pituwa.adapter.Implicits._

  def init = {
    val db = new H2Adapter
    db.execute(
      """CREATE TABLE IF NOT EXISTS WEB_PAGE
        |(ID INT AUTO_INCREMENT UNIQUE,
        | DOC_ID CHAR(256) PRIMARY KEY,
        | URL CHAR(256), TEXT_BODY CLOB);""".stripMargin)
  }

    def isInDb(text: String):Boolean = {
      val hash = FileOps.md5String(text)
      val sql = s"""SELECT * FROM WEB_PAGE WHERE DOC_ID = '$hash'"""
      val db = new H2Adapter
      db.select(sql).toStream.nonEmpty
    }

    def add(text: String, url: String) = {
      if (isInDb(text)) {
        val hash = FileOps.md5String(text)
        val sql = s"""INSERT INTO WEB_PAGE (DOC_ID, URL, TEXT_BODY) VALUES ('$hash', '$text', '$url')"""
        val db = new H2Adapter
        db.execute(sql)
      }
    }
}
