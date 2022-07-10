package analytics.consumer.yandex

import analytics.BaseAnalyticsConsumer
import analytics.Events
import analytics.consumer.yandex.event.YandexMetricaRevenueEvent
import analytics.event.DirectEvent
import analytics.event.Event
import com.yandex.metrica.Revenue
import com.yandex.metrica.YandexMetrica
import java.util.Currency

class YandexMetricaConsumer : BaseAnalyticsConsumer {

  constructor(events: Collection<String>) : super(events + YandexMetricaDirectEvent.REVENUE)
  constructor(events: Events) : super(events)

  override fun acceptDefaultEvent(event: Event) {
    YandexMetrica.reportEvent(
        event.event, event.params + (event.sum?.toParams() ?: emptyMap())
    )
  }

  override fun acceptDirectEvent(event: DirectEvent) {
    when (event) {
      is YandexMetricaRevenueEvent -> acceptRevenueEvent(event)
    }
  }

  private fun acceptRevenueEvent(event: YandexMetricaRevenueEvent) {
    Revenue.newBuilderWithMicros(event.sum.amountMicros(), Currency.getInstance(event.sum.currency))
        .withProductID(event.productId)
        .withQuantity(event.quantity)
        .withPayload(event.jsonPayload)
        .build()
        .run(YandexMetrica::reportRevenue)

  }
}
