package lk.pituwa.repository

import java.io.File

import com.typesafe.scalalogging.Logger
import lk.pituwa.adapter.H2Adapter
import lk.pituwa.utils.FileOps

/**
  * Created by nayana on 27/7/17.
  */
object DocumentRepository
{
  import lk.pituwa.adapter.Implicits._
  ////0d80 3456 - 0DFF 3583
  val logger = Logger("documents")

  val drop = "proc"

  var documents:List[String] = List[String]()

  val files: List[File]  = {
    val d = new File(drop)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def processed(file: File): Unit = {
    import java.io.File
    import java.nio.file.{Files, Path, StandardCopyOption}

    val hash = FileOps.computeHash(file.toPath.toString)
    val d1 = file.toPath
    val d2 = new File("archive/" + file.getName).toPath

    Files.move(d1, d2, StandardCopyOption.ATOMIC_MOVE)

    val db = new H2Adapter
    db.execute(s"""INSERT INTO DOCUMENT (DOCUMENT) VALUES ('${hash}')""")
    documents ++= List(hash)
  }

  def init = {
    val db = new H2Adapter
    db.execute("CREATE TABLE IF NOT EXISTS DOCUMENT (id INT AUTO_INCREMENT UNIQUE , DOCUMENT CHAR(256) PRIMARY KEY);")
  }

  lazy val boot = {
    val db = new H2Adapter
    db.select("SELECT DOCUMENT FROM DOCUMENT").toStream.map(v => {
      v.getString("DOCUMENT")
    })
  }

  def save = {

  }
}
