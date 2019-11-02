package converter

import org.scalatest.WordSpec
import server.model.ConvertResult
import server.model.rates.CurrencyRates

import scala.util.{Failure, Random, Success}

class CurrencyConverterTest extends WordSpec {

  private val currencyRates =
    CurrencyRates(
      "EUR",
      Map(
        "PLN" -> 4.2535,
        "CAD" -> 1.4682,
        "HKD" -> 8.7298,
        "AUD" -> 1.6151,
        "SEK" -> 10.6993,
        "TRY" -> 6.3761,
        "BRL" -> 4.4437,
        "KRW" -> 1300.09,
        "CZK" -> 25.514,
        "BGN" -> 1.9558,
        "GBP" -> 0.86008,
        "MXN" -> 21.3164,
        "THB" -> 33.623,
        "ISK" -> 138.1,
        "SGD" -> 1.5129,
        "CHF" -> 1.1013,
        "INR" -> 78.816,
        "CNY" -> 7.844,
        "PHP" -> 56.286,
        "RON" -> 4.7547,
        "DKK" -> 7.4712,
        "JPY" -> 120.43,
        "HUF" -> 328.33,
        "MYR" -> 4.64,
        "USD" -> 1.1139,
        "RUB" -> 71.0786,
        "NZD" -> 1.7326,
        "IDR" -> 15640.93,
        "ILS" -> 3.9272,
        "NOK" -> 10.1638,
        "ZAR" -> 16.828,
        "HRK" -> 7.46),
      "2019-11-01")

  private val avaliableCurrencyCodes: List[String] = currencyRates.base +: currencyRates.rates.keys.toList

  private val currencyConverter =
    CurrencyConverter(currencyRates)

  "CurrencyConverterTest" should {

    "(fail) no such currency code found" in {
      currencyConverter.convertCurrency("DDD", "RUB", 232) match {
        case Success(_) =>
          fail("There is wrong currency code! Should fail!")
        case Failure(exception) =>
          println(exception)
      }
    }

    "(successfully) convert currency (number == 0)" in {
      currencyConverter.convertCurrency("USD", "RUB", 0) match {
        case Success(value) =>
          assert(value == ConvertResult(0))
        case Failure(exception) =>
          fail(exception)
      }
    }

    "(successfully) convert currency" in {
      val random = new Random
      val currencyMap: Map[String, Double] = currencyRates.rates + (currencyRates.base -> 1.0)

      0 until 1000 foreach {
        _ =>
          val number = random.nextInt()
          val randomFrom = getRandomCurrencyCode(avaliableCurrencyCodes, random)
          val randomTo = getRandomCurrencyCode(avaliableCurrencyCodes, random)
          currencyConverter.convertCurrency(randomFrom, randomTo, number) match {
            case Success(value) =>
              assert(value == ConvertResult(number / currencyMap(randomFrom) * currencyMap(randomTo)))
            case Failure(exception) =>
              fail(exception)
          }
      }
    }
  }

  private def getRandomCurrencyCode(listOfCodes: List[String], random: Random): String =
    listOfCodes(random.nextInt(listOfCodes.length))
}
