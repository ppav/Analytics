package analytics

import analytics.event.Event

interface Interceptor {
  suspend fun intercept(
    event: Event
  ): Event
}