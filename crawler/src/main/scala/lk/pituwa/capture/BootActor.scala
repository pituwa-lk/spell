package lk.pituwa.capture

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.scalalogging.Logger
import lk.pituwa.capture.CrawlActor.Download
import lk.pituwa.capture.QueueActor.SendFirst
import lk.pituwa.model.Link
import lk.pituwa.repository.{DocumentRepository, InfoMapRepository, LinkRepository, WordRepository}

/**
  * Created by nayana on 28/7/17.
  */
object DataActor {
  def props: Props = Props[DataActor]

  trait Message
  final case class LoadData(p: String) extends Message
  final case class Initialize()
}

class DataActor(queueActor: ActorRef) extends Actor {
  import DataActor._

  val logger = Logger("Data Actor")

  val linkRepo = LinkRepository
  val textRepo = WordRepository
  val infoMapRepo  = InfoMapRepository
  val docRepo = DocumentRepository

  override def receive: Receive = {
    case Initialize => {
        linkRepo.init
        textRepo.init
        infoMapRepo.init
        docRepo.init
        self ! LoadData("boot!!!")
    }
    case LoadData(p) => {
      logger.info("Started Loading data from h2") //these events has to happen in serial form
      linkRepo.links   = linkRepo.nonVisited.toList
      linkRepo.crawled = linkRepo.visited.toList
      textRepo.words   = textRepo.boot
      infoMapRepo.infoMaps = infoMapRepo.boot.toList
      docRepo.documents = docRepo.boot.toList
      logger.info("Finished loading data with h2")
      //queueActor ! SendFirst
    }
  }
}
