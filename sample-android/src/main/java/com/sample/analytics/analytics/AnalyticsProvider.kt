package com.sample.analytics.analytics

import analytics.Analytics
import analytics.AnalyticsConsumer
import analytics.BaseAnalyticsConsumer
import analytics.ConsumerInterceptor
import analytics.Events.ALL
import analytics.Interceptor
import analytics.consumer.firebase.FirebaseConsumer
import analytics.consumer.yandex.YandexMetricaConsumer
import analytics.event.Event
import com.sample.analytics.analytics.event.eventArray
import com.google.firebase.analytics.FirebaseAnalytics
import com.sample.analytics.App
import com.sample.analytics.analytics.event.CheckoutAnalytics
import java.util.logging.Logger

object AnalyticsProvider {

  private lateinit var INSTANCE: Analytics

  private val YANDEX_EVENTS = listOf(*eventArray<CheckoutAnalytics.Events>())
  private val FIREBASE_EVENTS = listOf(CheckoutAnalytics.Events.ADDRESS_SELECTED.event)

  fun provide(): Analytics =
    if (::INSTANCE.isInitialized) INSTANCE
    else {

      INSTANCE = Analytics.Builder()
          .addConsumer(FooConsumer())
          .addConsumer(YandexMetricaConsumer(YANDEX_EVENTS))
          .addConsumer(FirebaseConsumer(FirebaseAnalytics.getInstance(App.INSTANCE), FIREBASE_EVENTS))
          .addInterceptor(CommonInterceptor())
          .addConsumerInterceptor(FooConsumerInterceptor())
//          .setExceptionHandler { error -> logger.warning(error.message ?: "") }
          .setDebugLog(true)
          .build()
      INSTANCE
    }

}

class CommonInterceptor : Interceptor {
  override suspend fun intercept(event: Event) =
    event.copy(params = event.params + mapOf("deviceId" to "ID", "userId" to "ID"))
}

//private val logger = Logger.getLogger("logger_analytics_sample")

class FooConsumer : BaseAnalyticsConsumer(ALL) {
  override fun acceptDefaultEvent(event: Event) {
//    logger.info("${this::class.simpleName} acceptEvent: $event")
  }
}

class FooConsumerInterceptor : ConsumerInterceptor {
  override suspend fun intercept(
    consumer: AnalyticsConsumer,
    event: Event
  ) = takeIf { consumer is FooConsumer }
      ?.let { event.copy(params = event.params + ("fooUserId" to "ID")) }
      ?: event
}

