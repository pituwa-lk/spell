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

  override def receive: Receive = {
    case SendFirst => {
      crawler ! Download(Link(link = "https://si.wikipedia.org/wiki/%E0%B6%B8%E0%B7%94%E0%B6%BD%E0%B7%8A_%E0%B6%B4%E0%B7%92%E0%B6%A7%E0%B7%94%E0%B7%80"))
    }
    case SendNext => {
      val url = LinkRepository.get
      crawler ! Download(url)
    }
  }
}
