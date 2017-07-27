package lk.pituwa.capture

import lk.pituwa.utils.LanguageDetector
import org.scalatest.FlatSpec

/**
  * Created by nayana on 26/7/17.
  */
class CaptureSpec extends FlatSpec
{
  "අසරැවා\\බාගත්\\" should "දත්ත\\හිස්\\නැතුව\\තිබිය\\යුතුය\\" in {
    val crawler = new Crawler(lk.Registry.registered.head)
    assert(crawler.crawl.body.nonEmpty)
  }

  "අසරැවා\\බාගත්\\" should "දත්ත\\හිස්\\නැතුව\\තිබිය\\යුdddතුය\\" in {
    val crawler = new Crawler(lk.Registry.registered.head)
    val sniffed = crawler.sniff
    assert(sniffed.body.nonEmpty)
    assert(sniffed.body === "text/html")
  }

  "CapturedLinks" should "be parsable from link extractor" in {
    val crawler = new Crawler(lk.Registry.registered.head)
    val linkExtractor = new LinkExtractor()
    val z = linkExtractor.extract(crawler.crawl)
    assert(z.nonEmpty)
    assert(z.head.startsWith("https://si.wikipedia.org/"))
  }

  "CapturedText" should "be parsable from text extractor" in {
    val crawler = new Crawler(lk.Registry.registered.head)
    val textExtractor = new TextExtractor()
    val z = textExtractor.extract(crawler.crawl)
    assert(z.nonEmpty)
    //assert(LanguageDetector.calculate(z.head) === "si")
  }
}

