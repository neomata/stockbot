package com.neomata.stockbot
package network

import java.io.BufferedInputStream
import java.net.{HttpURLConnection, URL, URLConnection}
import scala.concurrent.{ExecutionContext, Future}
import scala.io.{BufferedSource, Source}

object HttpHub {
  def get(url: String): String = {
    val site = new URL(url)
    val source: BufferedSource = Source.fromURL(site)
    source.mkString
  }
}