package lk.pituwa.adapter

import lk.pituwa.repository.LinkRepository
import org.scalatest.FlatSpec

/**
  * Created by nayana on 28/7/17.
  */
class H2AdapterFunSpec extends FlatSpec {

  "adapter" should "create table" in {
    val db = new H2Adapter
    db.execute("CREATE TABLE word (word CHAR(100) PRIMARY KEY, count INT)")
    assert(true)
  }

  "link repo" should "create the link table" in {
    LinkRepository.init
    val db = new H2Adapter
    db.execute("SELECT * FROM LINKS")
    assert(true)
  }
}
