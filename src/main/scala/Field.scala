package com.neomata.stockbot

import retailer.Retailer

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.neomata.stockbot.processing.{Rate, StockNotifier}
import scala.concurrent.duration._

object Field extends App {
  implicit val system: ActorSystem[_] = ActorSystem(Behaviors.ignore, "system")

//  val url = "https://www.walmart.com/ip/Sony-PlayStation-5-Digital-Edition/493824815"
  val url = "https://www.walmart.com/ip/Xbox-Series-X/443574645"
//  val url = "https://www.gamestop.com/consoles-hardware/playstation-5/consoles/products/sony-playstation-5-console/11108140.html?condition=Pre-Owned"
//  val url = "https://www.gamestop.com/consoles-hardware/xbox-series-x%7Cs/consoles/products/xbox-series-s-with-3-months-ultimate-game-pass-system-bundle/B224746X.html"
//  val url = "https://www.bestbuy.com/site/sony-playstation-5-console/6426149.p?skuId=6426149"
//  val url = "https://www.bestbuy.com/site/monster-hunter-world-iceborne-master-edition-deluxe-playstation-4-playstation-5/6355668.p?skuId=6355668"
//  val url = "candlehomie"
  val rate = Rate(times = 1, per = 30.seconds)

  val notifier = new StockNotifier(url, rate)

  notifier.observe()
}
