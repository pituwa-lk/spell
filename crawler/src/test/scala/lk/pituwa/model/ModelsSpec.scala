package lk.pituwa.model

import org.scalatest.FlatSpec

/**
  * Created by nayana on 27/7/17.
  */
class ModelsSpec extends FlatSpec
{
    "request" should "return protocol plus host when ask for prefix" in {
      val u = Request(Link("http://www.pituwa.lk/where/is/this"))
      assert(u.prefix === "http://www.pituwa.lk")
      assert(lk.Registry.registered.head.prefix === "https://si.wikipedia.org")
    }
}
