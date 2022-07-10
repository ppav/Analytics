package analytics

import analytics.Events.ALL
import analytics.event.AnalyticsEvent
import analytics.event.DirectEvent
import analytics.event.Event

enum class Events { ALL }

abstract class BaseAnalyticsConsumer(private val canAccept: (event: String) -> Boolean) :
    AnalyticsConsumer {

  constructor(events: Events) : this({ events == ALL })
  constructor(events: Collection<String>) : this(events::contains)

  final override fun canAccept(event: String) = canAccept.invoke(event)

  final override fun acceptEvent(event: AnalyticsEvent) {
    when (event) {
      is Event -> acceptDefaultEvent(event)
      is DirectEvent -> acceptDirectEvent(event)
    }
  }

  abstract fun acceptDefaultEvent(event: Event)
  open fun acceptDirectEvent(event: DirectEvent) {}
}