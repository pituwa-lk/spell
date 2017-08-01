package lk.pituwa.capture

import akka.actor.{Actor, ActorRef, Props}
import com.netaporter.uri.decoding.UriDecodeException
import com.typesafe.scalalogging.Logger
import lk.pituwa.capture.QueueActor.SendNext
import lk.pituwa.model._
import lk.pituwa.repository.{InfoMapRepository, LinkRepository, WWWDocumentRepository, WordRepository}

import scala.concurrent.Future

object CrawlActor {
  def props: Props = Props[CrawlActor]

  trait Message
  final case class Download(url: String) extends Message
}

class CrawlActor extends Actor {
  import CrawlActor._

  val logger = Logger("crawlactor")

  val linkExtractor = new LinkExtractor

  val textExtractor = new TextExtractor



  override def receive: Receive = {
    case Download(url) => {

      import scala.concurrent.ExecutionContext.Implicits.global

      val response = new Crawler(Request(url)).crawl
      val words = textExtractor.extract(response).filter(_.matches("""^[\u0D80-\u0DFF\u200D]+$""")).distinct
      logger.info("WebWord size is {}", words.size)
      val links = linkExtractor.extract(response).distinct.map(v => {
        try {
          Some(LinkRepository.sanitize(v))
        } catch {
          case e:UriDecodeException => {
            logger.info("bad link found {}", v)
            None
          }
        }
      }).filter(_.nonEmpty).map(_.get)
      //val document = Document(response, links, infoMap)
      LinkRepository.setCrawled(response.request.uri)
      LinkRepository.bulkAdd(links)
      WordRepository.add(words)
      sender() ! SendNext
    }
  }
}