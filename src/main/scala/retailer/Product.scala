package com.neomata.stockbot
package retailer

case class Product(url: String) {
  val retailer: Option[Retailer] = Retailer.fromUrl(url)
}
