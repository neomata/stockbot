package com.neomata.stockbot
package processing

import scala.concurrent.duration.{FiniteDuration, TimeUnit}

case class Rate(times: Long, per: FiniteDuration) {
  def units: TimeUnit = per.unit

  def timeLength: Long = per.length
}
