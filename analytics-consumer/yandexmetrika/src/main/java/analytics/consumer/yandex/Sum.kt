package analytics.consumer.yandex

import analytics.event.Sum

internal fun Sum.amountMicros(): Long = (amount * 1000000).toLong()

internal fun Sum.toParams(): Map<String, Any> {
  return mapOf(
      "Price" to amount,
      "PriceMicros" to amountMicros(),
      "Currency" to currency
  )
}