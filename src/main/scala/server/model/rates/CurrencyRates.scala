package server.model.rates

case class CurrencyRates(base: String, rates: Map[String, Double], date: String)
