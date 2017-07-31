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
      crawler ! Download(Link(link = "http://www.lankadeepa.lk/"))
    }
    case SendNext => {
      val url = LinkRepository.get
      crawler ! Download(url)
    }
  }
}
