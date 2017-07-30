package lk.pituwa.adapter

import lk.pituwa.repository.{LinkRepository, WordRepository}
import org.scalatest.FlatSpec


/**
  * Created by nayana on 28/7/17.
  */
class H2AdapterFunSpec extends FlatSpec {

  "adapter" should "create table" in {
    val db = new H2Adapter
    db.execute("CREATE TABLE foobar (word CHAR(100) PRIMARY KEY, count INT)")
    assert(true)
  }

  "link repo" should "create the link table" in {
    LinkRepository.init
    val db = new H2Adapter
    db.execute("SELECT * FROM LINKS")
    assert(true)
  }

  "std repo" should "insert record" in {
    val db = new H2Adapter
    WordRepository.init
    val id = db.execute("""INSERT INTO WORD (word, count) VALUES ('පියා', '1')""")
    assert(id > 0)
  }

  def score(p1: String, p2: String, matched: Int = 0):Int = {
    p1.charAt(0) == p2.charAt(0) match {
      case true =>
        if (p1.length > 1 && p2.length > 1) {
          score(p1.substring(1), p2.substring(1), matched + 1)
        }
        matched + 1
      case false => matched
    }
  }

  "search strategy" should "be able to find words" in {
    import Implicits._
    val prefix = "පි"
    val db = new H2Adapter
    val rs = db.select("""SELECT * FROM WORD""")
    val words = rs.toStream.map(r => {
      r.getString("word")
    }).filter(_.length >= prefix.length).filter(x => {
        val s = score(x, prefix, 0)
        s >= prefix.length - 1
    })
    println(words.size)
    /*val len = prefix.length
    val cl = (len to 1).toList //cl -> charLength
    cl.map(l => {
      words.filter(
        z => {
          z.charAt(l) == prefix.charAt(l)
        })
    })*/
  }
}
