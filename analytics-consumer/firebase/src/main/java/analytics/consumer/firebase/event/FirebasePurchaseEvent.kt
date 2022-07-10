package analytics.consumer.firebase.event

import analytics.consumer.firebase.FirebaseDirectEvent
import analytics.consumer.firebase.converter.toParams
import analytics.event.DirectEvent
import analytics.event.EventParamsBuilder
import analytics.event.Sum
import android.os.Parcelable
import com.google.firebase.analytics.FirebaseAnalytics

internal data class FirebasePurchaseEvent(
  val sum: Sum,
  val items: List<Parcelable> = emptyList(),
  val affiliation: String? = null,
  val coupon: String? = null,
  val shipping: Double? = null,
  val tax: Double? = null,
  val transactionId: String? = null,
) : DirectEvent(FirebaseDirectEvent.PURCHASE) {

  internal fun toMap(): Map<String, Any> = EventParamsBuilder()
      .withParam(FirebaseAnalytics.Param.AFFILIATION, affiliation)
      .withParam(FirebaseAnalytics.Param.ITEMS, items)
      .withParam(FirebaseAnalytics.Param.COUPON, coupon)
      .withParam(FirebaseAnalytics.Param.SHIPPING, shipping)
      .withParam(FirebaseAnalytics.Param.TAX, tax)
      .withParam(FirebaseAnalytics.Param.TRANSACTION_ID, transactionId)
      .build() + sum.toParams()
}

