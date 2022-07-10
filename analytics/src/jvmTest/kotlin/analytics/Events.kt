package analytics

import analytics.event.Event

private val currency = "USD"
private const val PRODUCT_NAME_PARAM = "product_name"

val Event0 = Event.Builder("event_0")
    .withParam("param", "event_0_param")
    .build()

val Event1 = Event.Builder("event_1")
    .withParam("param_event_1", "event_1_value")
    .build()

val Event2 = Event.Builder("event_2")
    .withParam("param_event_2", "event_2_value")
    .build()
