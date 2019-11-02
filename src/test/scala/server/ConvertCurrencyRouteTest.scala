package server

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import org.scalatest._
import server.model.{ConvertResult, CustomJsonSupport}
import server.routes.ConvertCurrencyRoute

class ConvertCurrencyRouteTest extends WordSpec with Matchers with ScalatestRouteTest with CustomJsonSupport {

  // USD = 1.1154
  // RUB = 71.3639
  // number = 42

  "Currency server" should {
    "convert currency from USD to RUB" in {
      Get("/convert?from=USD&to=RUB&number=42") ~>  ConvertCurrencyRoute.currencyRoute ~> check {
        Unmarshal(response).to[ConvertResult].map(result => result shouldEqual ConvertResult(2687.182894029048))
      }
    }
  }
}
