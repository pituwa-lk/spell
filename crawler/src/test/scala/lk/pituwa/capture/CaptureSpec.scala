package lk.pituwa.capture

import lk.pituwa.model._
import lk.pituwa.repository.{InfoMapRepository, LinkRepository, WWWDocumentRepository, WordRepository}
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
    val linkExtractor = new LinkExtractor

    val textExtractor = new TextExtractor
    val response = new Crawler(Request(Link("http://www.sinhalabuddhist.com/2014/11/islam-sharia-banking.html?showComment=1415070924582"))).crawl
    val word = textExtractor.extract(response).filter(_.matches("""^[\u0D80-\u0DFF\u200D]+$""")).distinct.toList

    val pgraph = """ලංකාවේ බැංකු කටයුතු පාලනය වන 1988 අංක 30 දරණ බැංකු කටයුතු පිළිබද පනත ඉස්ලාමීය බැංකුකරණයට ඉඩ සලසමින් සංශෝධනය වන්නේ 2005 දෙසැම්බර් මාසයේ දහ වැනිදාය. ඒ අනුව අමානා ඉස්ලාමීය මූල්‍ය සමාගම මුල් වරට ලංකාවේ සිය මෙහෙයුම් ආරම්භ කරමින් ඉස්ලාමීය ශරීයා බැංකු ක්‍රමය ලංකාවට හදුන්වා දෙන්නේ 2011 දී ය. රටටම හොර රහසේ තනිකරම ශරීයා නීතියට අනුගත මූල්‍ය ක්‍රමය පමණක් අනුගමනය කරන මෙම ආයතනයෙන් පමණක් ඇරඹි ශරීයාකරණය මේ වන විට රාජ්‍ය බැංකු හා පුද්ගලික මූල්‍ය ආයතන විස්සකට වැඩි ප්‍රමාණයක ක්‍රියාත්මක කර ඇති බව කියවේ."""

    val isrt = pgraph.split(" ").toList.intersect(word)
    val dist = pgraph.split(" ").distinct
    println("Size of the intersect is " + isrt.size + " and size of source is " + dist.size)



  }
    /*logger.info("WebWord size is {}", infoMap.size)
    val links = linkExtractor.extract(response).distinct.map(v => Link(LinkRepository.sanitize(v)))
    val document = Document(response, links, infoMap)
    LinkRepository.setCrawled(url)
    LinkRepository.bulkAdd(document.links)
    WordRepository.add(document.infoMap.map(v => v.word))
    InfoMapRepository.infoMaps ++ List(document.infoMap)
    WWWDocumentRepository.documents ++ List(document)
    val crawler = new Crawler()
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
  }*/
}

