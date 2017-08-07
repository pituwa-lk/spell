package lk.pituwa.adapter

import java.sql.{Connection, DriverManager, ResultSet}

import com.typesafe.config.ConfigFactory


object Implicits {
  implicit class ResultSetStream(resultSet: ResultSet) {

    def toStream: Stream[ResultSet] = {
      new Iterator[ResultSet] {
        def hasNext = {
          resultSet.next() match {
            case true => true
            case false => {
              resultSet.getStatement.getConnection.close()
              false
            }
          }
        }

        def next() = resultSet
      }.toStream
    }
  }
}
/**
  * Created by nayana on 28/7/17.
  */
class H2Adapter {

  lazy val config = ConfigFactory.load()

  lazy val driver = config.getString("db.driver")

  lazy val url = config.getString("db.url")

  lazy val username = config.getString("db.username")

  lazy val password = config.getString("db.password")

  var connection: Connection = null

  def execute(sql: String): Long = {
    //var connection: Connection = null
    var id:Long = 0
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement()
      statement.execute(sql)
      val genKeys = statement.getGeneratedKeys
      if (genKeys.next()) {
        id = genKeys.getLong(1)
      }
    } catch {
      case e:Throwable => e.printStackTrace
    }
    connection.close()
    id
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
      case e:Throwable => e.printStackTrace
    }
    //connection.close() //not able to close connection here, because result-set is still streaming
    resultSet
  }

  def close = {
    connection.close()
  }
}

//queue add instances