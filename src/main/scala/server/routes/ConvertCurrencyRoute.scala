package server.routes

import akka.http.scaladsl.server.{Directives, Route}
import api.ActualCurrencyRatesGetter
import converter.CurrencyConverter
import server.model.CustomJsonSupport
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import scala.util.{Failure, Success}

object ConvertCurrencyRoute extends Directives with CustomJsonSupport {

  val currencyRoute: Route =
    path("convert") {
      parameters('from.as[String], 'to.as[String], 'number.as[Double]) {
        (from, to, number) =>

          ActualCurrencyRatesGetter.getRates match {
            case Success(currencyMap) =>
              CurrencyConverter(currencyMap).convertCurrency(from, to, number) match {
                case Success(convertedValue) =>
                  complete(convertedValue)
                case Failure(exception) =>
                  complete {
                    HttpResponse(InternalServerError, entity = s"Cannot convert number: ${exception.getMessage}")
                  }
              }

            case Failure(exception) =>
              complete {
                HttpResponse(InternalServerError, entity = s"Cannot get actual rates: ${exception.getMessage}")
              }
          }
      }
    }
}
