package lk.pituwa.capture

import akka.actor.{Actor, ActorRef, Props}
import lk.pituwa.capture.CrawlActor.Download
import lk.pituwa.model.Link
import lk.pituwa.repository.LinkRepository


/**
  * Created by nayana on 27/7/17.
  */

object QueueActor {
  def props: Props = Props[QueueActor]

  trait Message
  final case class SendNext() extends Message
  final case class SendFirst()
}

class QueueActor(crawler: ActorRef) extends Actor {
  import QueueActor._

  var domainQueue = Iterator(
    "http://www.lankadeepa.lk", "http://www.divaina.com/", "http://www.silumina.lk/")

  override def receive: Receive = {
    case SendFirst => {
      crawler ! Download(domainQueue.next())
    }
    case SendNext => {
      val url = LinkRepository.links.headOption
      url match {
        case Some(e) => crawler ! Download(e)
        case None => {
          if (domainQueue.hasNext) {
            crawler ! Download(domainQueue.next())
          }

        }
      }

    }
  }
}
