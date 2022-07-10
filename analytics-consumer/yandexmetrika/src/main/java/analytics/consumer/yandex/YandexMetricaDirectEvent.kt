package analytics.consumer.yandex

import analytics.consumer.yandex.event.YandexMetricaRevenueEvent
import analytics.event.DirectEvent
import analytics.event.Sum

object YandexMetricaDirectEvent {
  internal const val REVENUE = "ya_revenue"

  /*
  * @See https://appmetrica.yandex.ru/docs/mobile-sdk-dg/android/ref/ru/yandex/metrica/Revenue.html
  * */
  fun revenue(
    sum: Sum,
    productId: String,
    quantity: Int? = null,
    jsonPayload: String? = null
  ): DirectEvent = YandexMetricaRevenueEvent(sum, productId, quantity, jsonPayload)
}