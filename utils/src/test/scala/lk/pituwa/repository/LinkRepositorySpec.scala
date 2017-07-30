package lk.pituwa.repository

import org.scalatest.FlatSpec

/**
  * Created by nayana on 30/7/17.
  */
class LinkRepositorySpec extends FlatSpec{

  "LinkRepo" should "Load the data as list of Link" in {
      val boot = LinkRepository.boot
      assert(boot.nonEmpty)

      val vLink = LinkRepository.visited.head
      assert(LinkRepository.isInDb(vLink))
  }

}
