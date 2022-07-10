package com.sample.analytics.analytics.event

import analytics.consumer.firebase.FirebaseDirectEvent
import analytics.consumer.yandex.YandexMetricaDirectEvent
import analytics.event.Event
import analytics.event.Sum
import com.sample.analytics.analytics.event.CheckoutAnalytics.Events.ADDRESS_SELECTED
import com.sample.analytics.analytics.event.CheckoutAnalytics.Events.PURCHASE
import com.sample.analytics.analytics.event.CheckoutAnalytics.Events.START

object CheckoutAnalytics {

  enum class Events(override val event: String) : EventScope {
    START("checkout_start"),
    ADDRESS_SELECTED("checkout_address_selected"),
    PURCHASE("checkout_purchase"),
  }

  private const val PRODUCT_NAME_PARAM = "product_name"

  private const val currency = "USD"

  fun startCheckout(
    sum: Double,
    productName: String
  ) = Event.Builder(START)
      .withSum(sum, currency)
      .withParam(PRODUCT_NAME_PARAM, productName)
      .build()

  fun selectAddress(productName: String) =
    Event.Builder(ADDRESS_SELECTED)
        .withParam(PRODUCT_NAME_PARAM, productName)
        .build()

  fun purchase(
    sum: Double,
    productName: String
  ) = listOf(
      Event.Builder(PURCHASE)
          .withParam(PRODUCT_NAME_PARAM, productName)
          .withSum(sum, currency)
          .build(),

      YandexMetricaDirectEvent.revenue(Sum(sum, currency), productName),
      FirebaseDirectEvent.purchase(Sum(sum, currency))
  )
}


