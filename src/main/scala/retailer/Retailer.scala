package com.neomata.stockbot
package retailer

import network.HttpHub

import akka.stream.scaladsl.Flow
import com.typesafe.scalalogging.StrictLogging
import java.net.URL
import okhttp3.{OkHttpClient, Request}
import scala.util.matching.Regex

object Retailer extends StrictLogging {
  private val log = logger

  case object Walmart extends Retailer {
    override def htmlStockSign: String = ">Add to cart</span></button>"

    override def requestFlow: Flow[String, String, _] = {
      // OkHttp
      Flow[String].map(url => new Request.Builder().url(url).build())
        .map(req => { val client = new OkHttpClient; client.newCall(req).execute.body.string() } )
      // Python
//      Flow[String].map(url => requests.get(url))
//        .map(res => res.data.toString())
    }
  }

  case object GameStop extends Retailer {
    override def htmlStockSign: String = "data-buttontext=\"Add to Cart\" data-buttontextnotavailable=\"Not Available\">Add to Cart</button>"
  }

  case object BestBuy extends Retailer {
    override def htmlStockSign: String = ">Add to Cart</button>"
  }

  case object Target extends Retailer {
    override def htmlStockSign: String = ""
  }

  case object Amazon extends Retailer {
    override def htmlStockSign: String = ""
  }

  def fromUrl(url: URL): Option[Retailer] = fromUrl(url.toString)

  def fromUrl(url: String): Option[Retailer] = {
    val protocolRegex = new Regex("http://|https://")
    val topLevelRegex = new Regex(".com|.net|.org")

    val withoutProtocol = protocolRegex.replaceFirstIn(url, new String)
    val split = withoutProtocol.split(topLevelRegex.regex)
    val name = split.headOption match {
      case Some(domain) => domain.reverse.takeWhile(_ != '.').reverse
      case None => return None
    }

    name match {
      case "walmart" => Some(Walmart)
      case "bestbuy" => Some(BestBuy)
      case "gamestop" => Some(GameStop)
      case "target" => Some(Target)
      case "amazon" => Some(Amazon)
      case _ => None
    }
  }
}

trait Retailer {
  def name: String = this.toString.toLowerCase

  def htmlStockSign: String

  // some HTTP request methods work better on certain sites than others
  // this default is Java Standard
  def requestFlow: Flow[String, String, _] = Flow[String].map(url => HttpHub.get(url))
}
