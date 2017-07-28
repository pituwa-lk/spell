package lk.pituwa.adapter

import java.sql.{Connection, DriverManager, ResultSet}


object Implicits {
  implicit class ResultSetStream(resultSet: ResultSet) {

    def toStream: Stream[ResultSet] = {
      new Iterator[ResultSet] {
        def hasNext = resultSet.next()

        def next() = resultSet
      }.toStream
    }
  }
}
/**
  * Created by nayana on 28/7/17.
  */
class H2Adapter {
  val driver = "org.h2.Driver"

  val url = "jdbc:h2:tcp://localhost/~/test"

  val username = "sa"

  val password = ""

  def execute(sql: String) = {
    var connection: Connection = null
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement()
      statement.execute(sql)
    } catch {
      case e => e.printStackTrace
    }
    connection.close()
  }

  def select(sql: String): ResultSet = {
    var connection: Connection = null
    var resultSet: ResultSet = null
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement()
      resultSet = statement.executeQuery(sql)
    } catch {
      case e => e.printStackTrace
    }
    //connection.close()
    resultSet
  }
}

//queue add instances