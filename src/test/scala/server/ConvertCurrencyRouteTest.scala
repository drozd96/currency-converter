package server

import akka.http.javadsl.model.HttpResponse
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import org.scalatest._
import server.model.{ConvertResult, CustomJsonSupport}
import server.routes.ConvertCurrencyRoute
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._

import scala.concurrent.Await
import scala.concurrent.duration._

class ConvertCurrencyRouteTest extends WordSpec with Matchers with ScalatestRouteTest with CustomJsonSupport {

  "Currency server" should {

    "(fail) convert currency with unknown currency code" in {
      Get("/convert?from=HHH&to=USD&number=3223.22")  ~> ConvertCurrencyRoute.currencyRoute ~> check {
        val result = Await.result(Unmarshal(response).to[HttpResponse], 1.second)
        result.status() shouldEqual InternalServerError
      }
    }

    "(successfully) convert currency from USD to RUB, number 0" in {
      Get("/convert?from=USD&to=RUB&number=0") ~>  ConvertCurrencyRoute.currencyRoute ~> check {
        val result = Await.result(Unmarshal(response).to[ConvertResult], 1.second)
        result shouldEqual ConvertResult(0)
      }
    }
  }
}
