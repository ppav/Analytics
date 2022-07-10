package analytics

import analytics.event.AnalyticsEvent
import analytics.event.DirectEvent
import analytics.event.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

class Analytics internal constructor(
  private val consumerRegistry: Collection<AnalyticsConsumer>,
  private val interceptors: Collection<Interceptor> = emptyList(),
  private val consumerInterceptors: Collection<ConsumerInterceptor> = emptyList(),
  private val exceptionHandler: CoroutineContext = EmptyCoroutineContext,
  private val debugLog: Boolean
) {

  private val executor = Executor()

  private val logger: Logger? = Logger().takeIf { debugLog }

  fun track(event: AnalyticsEvent) {
    listOf(event).forEach(::trackEvent)
  }

  fun track(events: List<AnalyticsEvent>) {
    events.forEach(::trackEvent)
  }

  fun track(vararg events_: AnalyticsEvent) {
    events_.forEach(::track)
  }

  private fun trackEvent(event: AnalyticsEvent) {
    CoroutineScope(executor.dispatcher())
        .plus(exceptionHandler)
        .launch {
          when (event) {
            is DirectEvent -> trackDirectEvent(event)
            is Event -> trackGenericEvent(event)
          }
        }
  }

  private suspend fun trackDirectEvent(event: DirectEvent) {
    consumerRegistry
        .filter { consumer -> consumer.canAccept(event.event) }
        .forEach { consumer -> acceptCatching(event).invoke(consumer) }
  }

  private suspend fun trackGenericEvent(genericEvent: Event) {
    interceptors.fold(genericEvent) { acc, interceptor -> interceptor.intercept(acc) }
        .let { event ->
          consumerRegistry
              .filter { consumer -> consumer.canAccept(event.event) }
              .forEach { consumer ->
                consumerInterceptors.fold(event) { acc, interceptor ->
                  interceptor.intercept(consumer, acc)
                }
                    .let { acceptCatching(it).invoke(consumer) }
              }
        }

  }

  private fun acceptCatching(
    event: AnalyticsEvent
  ): suspend (AnalyticsConsumer) -> Unit = {

    logger?.info(
       " \n\tSend to ==> ${it::class.simpleName?:it}" +
            "\n\tEvent ==> $event"
    )
    try {
      it.acceptEvent(event)
    } catch (e: Exception) {
      (exceptionHandler as? CoroutineExceptionHandler)?.handleException(coroutineContext, e)
      logger?.error("Error accept event: $event", e)
    }
  }

  companion object
  class Builder {
    private var interceptors: Set<Interceptor> = emptySet()
    private var consumerInterceptors: Set<ConsumerInterceptor> = emptySet()
    private var consumerRegistry: Set<AnalyticsConsumer> = emptySet()
    private var exceptionHandler: CoroutineExceptionHandler? = null
    private var debugLog = false

    fun addConsumer(consumer: AnalyticsConsumer) = this.also {
      consumerRegistry = consumerRegistry + consumer
    }

    fun addInterceptor(interceptor: Interceptor) = this.also {
      interceptors = interceptors + interceptor
    }

    fun addConsumerInterceptor(consumerInterceptor: ConsumerInterceptor) = this.also {
      consumerInterceptors = consumerInterceptors + consumerInterceptor
    }

    fun setDebugLog(shouldEnable: Boolean) = this.also { debugLog = shouldEnable }

    fun setExceptionHandler(exceptionHandler: (Throwable) -> Unit) = this.also {
      this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        exceptionHandler.invoke(throwable)
      }
    }

    fun build() = Analytics(
        consumerRegistry,
        interceptors,
        consumerInterceptors,
        exceptionHandler ?: EmptyCoroutineContext,
        debugLog
    )
  }
}