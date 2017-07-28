package lk.pituwa.capture

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.scalalogging.Logger
import lk.pituwa.capture.CrawlActor.Download
import lk.pituwa.repository.LinkRepository.{crawled, nonB, service, vis}
import lk.pituwa.repository.{LinkRepository, WordRepository}

/**
  * Created by nayana on 28/7/17.
  */
object DataActor {
  def props: Props = Props[CrawlActor]

  trait Message
  final case class LoadData(p: String) extends Message
}

class DataActor(crawlActor: ActorRef) extends Actor {
  import DataActor._

  val logger = Logger("Data Actor")

  val linkRepo = LinkRepository
  val textRepo = WordRepository

  override def receive: Receive = {
    case LoadData(p) => {
      logger.info("Started Loading data from h2")
      linkRepo.links = linkRepo.nonB.toList
      linkRepo.crawled = linkRepo.vis.toList
      textRepo.words = textRepo.boot
      logger.info("Finished loading data with h2")

      crawlActor ! Download(linkRepo.get)

    }
  }
}
