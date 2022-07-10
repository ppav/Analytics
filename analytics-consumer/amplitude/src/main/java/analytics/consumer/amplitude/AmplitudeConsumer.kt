package analytics.consumer.amplitude

import analytics.BaseAnalyticsConsumer
import analytics.Events
import analytics.event.DirectEvent
import analytics.event.Event
import analytics.event.EventParamsBuilder
import com.amplitude.api.AmplitudeClient
import org.json.JSONObject

class AmplitudeConsumer : BaseAnalyticsConsumer {
  constructor(
    amplitudeClient: AmplitudeClient,
    events: Events
  ) : super(events) {
    client = amplitudeClient
  }

  constructor(
    amplitudeClient: AmplitudeClient,
    events: Collection<String>
  ) : super(events) {
    client = amplitudeClient
  }

  private val client: AmplitudeClient

  override fun acceptDirectEvent(event: DirectEvent) {}

  override fun acceptDefaultEvent(event: Event) {
    client.logEvent(
        event.event, JSONObject(event.params
        .plus(event.sum?.let { sum ->
          EventParamsBuilder()
              .withParam("sum", sum.amount)
              .withParam("currency", sum.currency)
              .build()
        } ?: emptyMap()))
    )
  }
}