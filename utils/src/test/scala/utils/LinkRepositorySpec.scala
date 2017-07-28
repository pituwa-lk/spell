package utils

import lk.pituwa.repository.LinkRepository
import org.scalatest.FlatSpec

/**
  * Created by nayana on 28/7/17.
  */
class LinkRepositorySpec extends FlatSpec{

  "link repository" should "not add duplicates to the to be crawled list" in {
    LinkRepository.links = List("2", "1", "3")
    LinkRepository.crawled = List.empty
    assert(LinkRepository.get == "2")
    LinkRepository.setCrawled("2")
    LinkRepository.bulkAdd(List("2", "5", "6"))
    assert(!LinkRepository.links.contains("2"))
  }

}
