package server.routes

import akka.http.scaladsl.server.{Directives, Route}
import server.model.{ConvertResult, CustomJsonSupport}
import server.model.rates.CurrencyRates

import scala.io.Source

object ConvertCurrencyRoute extends Directives with CustomJsonSupport {

  System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36")

  import spray.json._
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  val currencyRoute: Route =
    path("convert") {
      parameters('from.as[String], 'to.as[String], 'number.as[Double]) {
        (from, to, number) =>
          val currencyMap = Source.fromURL("https://api.ratesapi.io/api/latest").mkString.parseJson.convertTo[CurrencyRates]
          val fromCoef = if(currencyMap.base == from) 1 else currencyMap.rates(from)
          val toCoef = if(currencyMap.base == to) 1 else currencyMap.rates(to)

          complete {
            ConvertResult(number / fromCoef * toCoef)
          }
      }
    }
}
