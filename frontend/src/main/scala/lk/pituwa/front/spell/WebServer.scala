package lk.pituwa.front.spell

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import lk.pituwa.capture.CrawlActor
import lk.pituwa.capture.CrawlActor.Download

import scala.concurrent.Future
import scala.io.StdIn

object WebServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("pituwalk")
    val crawler: ActorRef = system.actorOf(CrawlActor.props, "crawlerActor")

    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      } ~ path("crawl") {
        get {
          complete(
            Future.successful{
              crawler ! Download("http://www.agoda.com")
              "hello"
            }
          )
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 1593)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}