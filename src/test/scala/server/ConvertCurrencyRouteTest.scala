package server

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import api.ActualCurrencyRatesGetter
import org.scalatest._
import server.model.rates.CurrencyRates
import server.model.{ConvertResult, CustomJsonSupport}
import server.routes.ConvertCurrencyRoute

import scala.concurrent.Await
import scala.concurrent.duration._

class ConvertCurrencyRouteTest extends WordSpec with Matchers with ScalatestRouteTest with CustomJsonSupport {

  // USD = 1.1154
  // RUB = 71.3639
  // number = 42

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  val currentCurrencyRates: CurrencyRates = ActualCurrencyRatesGetter.getRates match {
    case scala.util.Success(value) =>
      value
  }

  println(currentCurrencyRates)

  "Currency server" should {
    "convert currency from USD to RUB" in {
      Get("/convert?from=USD&to=RUB&number=42") ~>  ConvertCurrencyRoute.currencyRoute ~> check {
        val result = Await.result(Unmarshal(response).to[ConvertResult], 1.second)
        result shouldEqual ConvertResult(2687.182894029048)
      }
    }
  }
}
