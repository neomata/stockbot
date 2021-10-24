package com.neomata.stockbot
package processing

import retailer.Product

import akka.NotUsed
import akka.actor.ClassicActorSystemProvider
import akka.http.scaladsl.{Http, HttpExt}
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.scalalogging.{Logger, StrictLogging}
import scala.concurrent.{ExecutionContextExecutor, Future}

class StockNotifier(url: String, rate: Rate)(implicit system: ClassicActorSystemProvider) extends StrictLogging {
  implicit private val executor: ExecutionContextExecutor = system.classicSystem.dispatcher

  private def log: Logger = logger

  private var live: Boolean = false

  private val product: Product = Product(url)

  private val http: HttpExt = Http(system)

  def observe(): Unit = product.retailer match {
    case Some(_) => if (!live) {
      log.info(s"Stock notifier for ${product.url} is live!")

      live = true
      val future: Future[Unit] = Source.repeat(NotUsed)
        .throttle(rate.times.toInt, rate.per)
        .map(notUsed => { log.info(s"Stock notifier for ${product.url} is still running"); notUsed })
        .map(_ => url)
        .via(product.retailer.get.requestFlow)
        .map(str => { println(str); str })
        .filter(page => page.contains(product.retailer.get.htmlStockSign))
        .map(_ => log.info(s"Product from ${product.retailer.get.name}: ${product.url} is in stock!"))
        .runWith(Sink.head)

      future.onComplete { _ =>
        log.info(s"Stock notifier for ${product.url} has ended")
      }
    } else {
      log.error(s"Stock notifier for ${product.url} is already live!")
    }
    case None => log.warn(s"StockBot does not support queries on this retailer")
  }
}
