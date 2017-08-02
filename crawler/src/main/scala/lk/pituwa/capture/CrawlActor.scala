package lk.pituwa.capture

import akka.actor.{Actor, Props}
import com.netaporter.uri.decoding.UriDecodeException
import com.typesafe.scalalogging.Logger
import lk.pituwa.capture.QueueActor.SendNext
import lk.pituwa.model._
import lk.pituwa.repository.{DocumentRepository, LinkRepository, WordRepository}

object CrawlActor {
  def props: Props = Props[CrawlActor]

  trait Message
  final case class Download(url: String) extends Message
}

class CrawlActor extends Actor {
  import CrawlActor._

  val logger = Logger("crawler")
  val linkExtractor = new LinkExtractor
  val textExtractor = new TextExtractor



  override def receive: Receive = {
    case Download(url) => {

      val response = new Crawler(Request(url)).crawl
      if (response.status == 200) {
        val textBody = textExtractor.extract(response)
        val words = textBody.split(" ").toList.filter(_.matches("""^[\u0D80-\u0DFF\u200D]+$""")).distinct
        logger.info("WebWord size is {}", words.size)
        val links = linkExtractor.extract(response).distinct.map(v => {
          try {
            Some(LinkRepository.sanitize(v))
          } catch {
            case e: UriDecodeException => {
              logger.info("bad link found {}", v)
              None
            }
          }
        }).filter(_.nonEmpty).map(_.get)
        LinkRepository.setCrawled(response.request.uri)
        LinkRepository.setCrawled(response.request.uri)
        LinkRepository.bulkAdd(links)
        WordRepository.add(words, response.request.url.host.get)
      } else {
        LinkRepository.delCrawled(response.request.uri)
      }

      //DocumentRepository.add(textBody.filter(_.matches("""^[\u0D80-\u0DFF\u200D]+$""")), response.request.uri)
      sender() ! SendNext
    }
  }
}