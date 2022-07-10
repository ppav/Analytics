package analytics

import analytics.event.AnalyticsEvent

interface AnalyticsConsumer {
  fun canAccept(event: String): Boolean
  fun acceptEvent(event: AnalyticsEvent)
}