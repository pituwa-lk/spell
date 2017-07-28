package lk.pituwa.capture

import akka.actor.{Actor, ActorRef, Props}
import lk.pituwa.capture.QueueActor.SendNext
import lk.pituwa.model.{Document, Request}
import lk.pituwa.repository.LinkRepository._
import lk.pituwa.repository.{LinkRepository, WordRepository}


object CrawlActor {
  def props: Props = Props[CrawlActor]

  trait Message
  final case class Download(url: String) extends Message
}

class CrawlActor extends Actor {
  import CrawlActor._

  val linkExtractor = new LinkExtractor

  val textExtractor = new TextExtractor

  override def receive: Receive = {
    case Download(url) => {

      val response = new Crawler(Request(url)).crawl
      LinkRepository.setCrawled(url)
      val links = linkExtractor.extract(response)
      val texts = textExtractor.extract(response)
      val document = Document(response, links, texts)
      LinkRepository.bulkAdd(document.links)
      WordRepository.add(document.texts.mkString(" ").split(" ").toList)
      WebServer.sendNext
    }
  }
}