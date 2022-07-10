package analytics.consumer.yandex.event

import analytics.consumer.yandex.YandexMetricaDirectEvent
import analytics.event.DirectEvent
import analytics.event.Sum

internal data class YandexMetricaRevenueEvent internal constructor(
  val sum: Sum,
  val productId: String,
  val quantity: Int?,
  val jsonPayload: String?
) : DirectEvent(YandexMetricaDirectEvent.REVENUE)
