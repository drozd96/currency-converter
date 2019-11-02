package converter

import server.model.ConvertResult
import server.model.rates.CurrencyRates

import scala.util.Try

object CurrencyConverter {
  def apply(actualCurrencyRates: CurrencyRates): CurrencyConverter =
    new CurrencyConverter(actualCurrencyRates)
}

class CurrencyConverter(actualCurrencyRates: CurrencyRates) {

  private val currencyRates = actualCurrencyRates.rates + (actualCurrencyRates.base -> 1.0)

  def convertCurrency(from: String, to: String, number: Double): Try[ConvertResult] =
    Try {
      val fromCoef = currencyRates(from)
      val toCoef = currencyRates(to)
      ConvertResult(number / fromCoef * toCoef)
    }
}
