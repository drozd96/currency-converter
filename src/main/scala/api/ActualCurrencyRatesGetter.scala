package api

import com.typesafe.config.ConfigFactory
import server.model.CustomJsonSupport
import server.model.rates.CurrencyRates

import scala.io.Source
import scala.util.Try

object ActualCurrencyRatesGetter extends CustomJsonSupport {

  import spray.json._

  private val config = ConfigFactory.load()

  // to avoid 403 forbidden
  System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36")

  def getRates: Try[CurrencyRates] =
    Try {
      Source.fromURL(config.getString("api.path")).mkString.parseJson.convertTo[CurrencyRates]
    }
}