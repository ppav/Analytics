package analytics

import analytics.event.Event

interface ConsumerInterceptor {
  suspend fun intercept(
    consumer: AnalyticsConsumer,
    event: Event
  ): Event
}