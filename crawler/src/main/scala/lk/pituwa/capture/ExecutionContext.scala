package lk.pituwa.capture

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import lk.pituwa.capture.BootActor.{Initialize, LoadData}
import scala.concurrent.{ExecutionContextExecutor, Future}


object ShutdownSave extends Thread {
  override def run = {
    //anycode we need to execute
  }
}

object ExecutionContext extends App {

  implicit val system = ActorSystem("pituwalk")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val crawlActor: ActorRef = system.actorOf(Props[CrawlActor], "crawlActor")
  val queueActor: ActorRef = system.actorOf(Props(classOf[QueueActor], crawlActor), "queueActor")
  val bootActor: ActorRef = system.actorOf(Props(classOf[BootActor], queueActor), "bootActor")

  bootActor ! Initialize
}