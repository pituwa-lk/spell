package lk.pituwa.capture

import jdk.nashorn.internal.ir.RuntimeNode.Request
import lk.pituwa.indexer.IndexController
import lk.pituwa.model.Document
import lk.pituwa.repository.LinkRepository

import scala.concurrent.Future

/**
  * Created by nayana on 27/7/17.
  */
class CaptureController {

  def run: Unit = {

    Future {

      val idx = new IndexController
      //get body
      //extract links
      //get text
      val linkExtractor = new LinkExtractor
      val textExtractor = new TextExtractor
      lk.Registry.registered.map(request => {
        val response = new Crawler(request).crawl
        val links = linkExtractor.extract(response)
        val texts = textExtractor.extract(response)
        val document = Document(response, links, texts)
        LinkRepository.bulkAdd(document.links)
        idx.accept(document)
      })
    }


    //send links for crawling
    //send document for indexing
  }
}
