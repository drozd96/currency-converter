package server.model

import server.model.rates.CurrencyRates
import spray.json.{DefaultJsonProtocol, PrettyPrinter, RootJsonFormat}

trait CustomJsonSupport extends DefaultJsonProtocol {
  implicit val printer: PrettyPrinter.type = PrettyPrinter
  implicit val convertResultFormat: RootJsonFormat[ConvertResult] = jsonFormat1(ConvertResult)
  implicit val currencyRateFormat: RootJsonFormat[CurrencyRates] = jsonFormat3(CurrencyRates)
}