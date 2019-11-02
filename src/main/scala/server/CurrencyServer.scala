package server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import server.routes.ConvertCurrencyRoute

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object CurrencyServer extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = ConvertCurrencyRoute.currencyRoute

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println("Server in online at http://localhost:8080")
  println("Press any key to stop it...")

  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
