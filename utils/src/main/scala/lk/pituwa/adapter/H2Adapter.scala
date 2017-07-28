package lk.pituwa.adapter

import java.sql.{Connection, DriverManager}

/**
  * Created by nayana on 28/7/17.
  */
class H2Adapter
{
    val driver = "org.h2.Driver"
    val url = "jdbc:h2:tcp://localhost/~/test"
    val username = "sa"
    val password = ""


  def execute(sql: String) = {
    var connection:Connection = null
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

  def select(sql: String) = {
    var connection:Connection = null
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("CREATE TABLE word (word CHAR(100) PRIMARY KEY, count INT)")
      while ( resultSet.next() ) {
        val host = resultSet.getString("word")
        val user = resultSet.getString("count")
        println("host, user = " + host + ", " + user)
      }
    } catch {
      case e => e.printStackTrace
    }
    connection.close()
  }
}
//queue add instances