package analytics

import analytics.event.AnalyticsEvent
import analytics.event.Event
import analytics.event.EventParamsBuilder
import io.mockk.MockKMatcherScope
import io.mockk.coEvery
import io.mockk.every
import org.junit.Before
import org.junit.Test
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify

class AnalyticsTests {

  private fun MockKMatcherScope.matchEvent(other: AnalyticsEvent) =
    match<AnalyticsEvent> { it.event == other.event }

  private fun MockKMatcherScope.mathEventParams(vararg params: Pair<String, Any>) =
    mathEventParams(mapOf(*params))

  private fun MockKMatcherScope.mathEventParams(params: Map<String, Any>) =
    match<Event> { event -> params.all { event.params[it.key] == it.value } }

  private fun MockKMatcherScope.matchEvents(vararg other: AnalyticsEvent) =
    match<AnalyticsEvent> { event -> other.any { it.event == event.event } }

  private val firstConsumer = mockk<AnalyticsConsumer>()
  private val secondConsumer = mockk<AnalyticsConsumer>()

  private val firstInterceptor = mockk<ConsumerInterceptor>()
  private val secondInterceptor = mockk<ConsumerInterceptor>()

  private var mockExceptionHandler: (Throwable) -> Unit =
    spyk({ e -> println("EXCEPTION:====> ${e.message}") })

  private lateinit var analytics: Analytics

  @Before
  fun setUp() {

    analytics = Analytics.Builder()
        .addConsumer(firstConsumer)
        .addConsumer(secondConsumer)
        .addConsumerInterceptor(firstInterceptor)
        .addConsumerInterceptor(secondInterceptor)
        .addConsumerInterceptor(ThreadLogInterceptor())
        .setExceptionHandler(mockExceptionHandler)
        .setDebugLog(true)
        .build()

    every { firstConsumer.canAccept(any()) } returns true
    every { secondConsumer.canAccept(any()) } returns true

    every { secondConsumer.acceptEvent(any()) } answers { }
    every { firstConsumer.acceptEvent(any()) } answers { }

    coEvery { firstInterceptor.intercept(any(), any()) } coAnswers { secondArg() }
    coEvery { secondInterceptor.intercept(any(), any()) } coAnswers { secondArg() }
  }

  @Test fun `send one event`() {
    every { secondConsumer.canAccept(any()) } answers { firstArg() as String == Event0.event }

    analytics.track(Event0)

    verify(timeout = 1000, exactly = 1) { firstConsumer.acceptEvent(matchEvents(Event0)) }
    verify(timeout = 1000, exactly = 1) { secondConsumer.acceptEvent(matchEvent(Event0)) }

  }

  @Test fun `second consumer accept only one event`() {
    every { secondConsumer.canAccept(any()) } answers { firstArg() as String == Event0.event }

    analytics.track(Event0, Event1)

    verify(timeout = 1000, exactly = 2) { firstConsumer.acceptEvent(matchEvents(Event0, Event1)) }

    verify(timeout = 1000, exactly = 1) { secondConsumer.acceptEvent(matchEvent(Event0)) }
    verify(timeout = 1000, inverse = true) { secondConsumer.acceptEvent(matchEvent(Event1)) }
    verify(timeout = 1000, inverse = true) { secondConsumer.acceptEvent(matchEvent(Event2)) }
  }

  @Test fun `first interceptor add param to one event for one consumer`() {
    coEvery { firstInterceptor.intercept(any(), any()) } coAnswers {
      (secondArg() as Event)
          .takeIf { it == Event0 }
          ?.takeIf { firstArg() as AnalyticsConsumer == firstConsumer }
          ?.let { it.copy(params = it.params + ("p" to "123")) }
          ?: secondArg()
    }

    analytics.track(Event0, Event1)

    verify(timeout = 1000, exactly = 1) { firstConsumer.acceptEvent(mathEventParams("p" to "123")) }
    verify(timeout = 1000, exactly = 2) { secondConsumer.acceptEvent(matchEvents(Event0, Event1)) }
  }

  @Test
  fun `broken consumers does not affect others`() {
    every { firstConsumer.acceptEvent(any()) } throws Exception("consumer is broken")

    analytics.track(Event1)
    verify(timeout = 1000, exactly = 1) { firstConsumer.acceptEvent(matchEvent(Event1)) }
    verify(timeout = 1000, exactly = 1) { secondConsumer.acceptEvent(matchEvent(Event1)) }
    verify(timeout = 1000, exactly = 1) { mockExceptionHandler(any()) }

  }

  @Test
  fun `broken interceptor affect all`() {
    coEvery { firstInterceptor.intercept(any(), any()) } throws Exception("interceptor is broken")

    analytics.track(Event1)
    verify(timeout = 1000, inverse = true) { firstConsumer.acceptEvent(matchEvent(Event1)) }
    verify(timeout = 1000, inverse = true) { secondConsumer.acceptEvent(matchEvent(Event1)) }
    verify(timeout = 1000, exactly = 1) { mockExceptionHandler(any()) }
  }

  class ThreadLogInterceptor : Interceptor, ConsumerInterceptor {
    override suspend fun intercept(event: Event): Event {
      println("Intercept on Thread: ${Thread.currentThread().name}")
      return event
    }

    override suspend fun intercept(
      consumer: AnalyticsConsumer,
      event: Event
    ): Event {
      println("ConsumerIntercept on Thread: ${Thread.currentThread().name}")
      return event
    }
  }
}

