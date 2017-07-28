package lk.pituwa.repository

// Use H2Driver to connect to an H2 database

import slick.jdbc.H2Profile.api._
import java.sql.DriverManager
import java.sql.Connection
/**
  * Created by nayana on 28/7/17.
  */
object RepositoryController extends App {

  override def main(args: Array[String]):Unit = ???

  class Word(tag: Tag) extends Table[(String, Int)](tag, "WORDS") {
    def word = column[String]("WORD", O.PrimaryKey)
    def count = column[Int]("COUNT")
    override def *  = (word, count)
  }
  val words = TableQuery[Word]

  def init:Unit = {
    val db = Database.forConfig("h2mem1")
    try {
      db.run(DBIO.seq(words.schema.create))
    } finally db.close
  }
}
//slick.basic.BasicBackend.action
//val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
//db.source.createConnection()

