package lk.pituwa.capture

import jdk.nashorn.internal.ir.RuntimeNode.Request
import lk.pituwa.model.Document
import lk.pituwa.repository.LinkRepository

/**
  * Created by nayana on 27/7/17.
  */
class CaptureController {

  def run: Unit = {

    //get body
    //extract links
    //get text
    val linkExtractor = new LinkExtractor
    val textExtractor = new TextExtractor
    val documents = lk.Registry.registered.map(request => {
      val response = new Crawler(request).crawl
      val links = linkExtractor.extract(response)
      val texts = textExtractor.extract(response)
      val document = Document(response, links, texts)

      LinkRepository.bulkAdd(document.links)


    })


    //send links for crawling
    //send document for indexing
  }
}
