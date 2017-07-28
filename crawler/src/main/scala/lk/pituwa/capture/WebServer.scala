package lk.pituwa.capture

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import lk.pituwa.capture.CrawlActor.Download
import lk.pituwa.capture.QueueActor.SendNext
import lk.pituwa.repository.{LinkRepository, WordRepository}
import lk.pituwa.service.WordService
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.headers.{Allow, Origin, `Access-Control-Allow-Origin`}
import lk.pituwa.capture.DataActor.LoadData
import spray.json.DefaultJsonProtocol._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

object ShutdownSave extends Thread {
  override def run = {
    LinkRepository.save
    WordRepository.save
  }
}

object WebServer {

  implicit val system = ActorSystem("pituwalk")

  val crawler: ActorRef = system.actorOf(Props[CrawlActor], "crawler")
  val queue: ActorRef = system.actorOf(Props(classOf[QueueActor], crawler), "queue")
  val dataAct: ActorRef = system.actorOf(Props(classOf[DataActor], crawler), "dataAct")

  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]) {

    import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

    Runtime.getRuntime.addShutdownHook(ShutdownSave)
    val route = cors() {
      path("lookup") {
        parameter('lookup) {
          lookup => {
            get {
              complete {
                WordService.getWordWithPrefix(lookup)
              }
            }
          }
        }
      }
    } ~ path("lvar") {
        parameters('lookup, 'size.as[Int]) { (lookup, size) => { get {
          complete(WordService.getWordWithPrefix(lookup, size))
        }
      }
    }
  }

    dataAct ! LoadData("Good Morning")

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

  def sendNext = {
    queue ! SendNext
  }
}