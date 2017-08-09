package lk.pituwa.frontend

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import lk.pituwa.service.WordService
import spray.json.DefaultJsonProtocol._

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn


object WebServer {

  implicit val system = ActorSystem("pituwalk")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]) {

    import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

    val route = cors() {
      path("spell") {
        post {
          entity(as[String]) { document => complete(WordService.spellCheck(document)) }
        }
      }
    }

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)
    println(s"Spell checker is online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}