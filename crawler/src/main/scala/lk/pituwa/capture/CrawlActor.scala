package lk.pituwa.capture

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.scalalogging.Logger
import lk.pituwa.capture.QueueActor.SendNext
import lk.pituwa.model._
import lk.pituwa.repository.{InfoMapRepository, LinkRepository, WWWDocumentRepository, WordRepository}
import scala.concurrent.Future

object CrawlActor {
  def props: Props = Props[CrawlActor]

  trait Message
  final case class Download(url: Link) extends Message
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
      val infoMap = textExtractor.extract(response).filter(_.matches("""^[\u0D80-\u0DFF\u200D]+$""")).distinct.map(v => {
        InfoMap(Some(Word(v)), Some(response.request.uri))
      })
      logger.info("WebWord size is {}", infoMap.size)
      val links = linkExtractor.extract(response).distinct.map(v => Link(LinkRepository.sanitize(v)))
      val document = Document(response, links, infoMap)
      LinkRepository.setCrawled(url)
      LinkRepository.bulkAdd(document.links)
      WordRepository.add(document.infoMap.map(v => v.word.get))
      Future {
        document.infoMap.foreach(imp => {
          InfoMapRepository.add(imp)
        })
      }
      sender() ! SendNext
    }
  }
}