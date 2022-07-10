package com.sample.analytics.analytics.event

import analytics.event.Event

interface EventScope {
  val event get() = ""
}

inline fun <reified T> eventArray(): Array<String> where T : Enum<T>, T : EventScope {
  return enumValues<T>()
      .map { it.event }
      .toTypedArray()
}

fun Event.Companion.Builder(eventScope: EventScope) = Event.Builder(eventScope.event)
