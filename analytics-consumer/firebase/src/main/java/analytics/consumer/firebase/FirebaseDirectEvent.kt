package analytics.consumer.firebase

import analytics.consumer.firebase.event.FirebaseLoginEvent
import analytics.consumer.firebase.event.FirebasePurchaseEvent
import analytics.consumer.firebase.event.FirebaseRefundEvent
import analytics.consumer.firebase.event.FirebaseSignUpEvent
import analytics.event.DirectEvent
import analytics.event.Sum
import android.os.Parcelable

/*
* @See https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Event
*/
object FirebaseDirectEvent {
  internal const val PURCHASE = "firebase_purchase"
  internal const val LOGIN = "firebase_login"
  internal const val SIGN_UP = "firebase_signup"
  internal const val REFUND = "firebase_refund"

  internal val EVENTS = listOf(PURCHASE, REFUND, LOGIN, SIGN_UP)

  fun purchase(
    sum: Sum,
    items: List<Parcelable> = emptyList(),
    affiliation: String? = null,
    coupon: String? = null,
    shipping: Double? = null,
    tax: Double? = null,
    transactionId: String? = null
  ): DirectEvent = FirebasePurchaseEvent(
      sum, items, affiliation, coupon,
      shipping, tax, transactionId
  )

  fun refund(
    sum: Sum,
    items: List<Parcelable> = emptyList(),
    affiliation: String? = null,
    coupon: String? = null,
    shipping: Double? = null,
    tax: Double? = null,
    transactionId: String? = null
  ): DirectEvent = FirebaseRefundEvent(
      sum, items, affiliation, coupon,
      shipping, tax, transactionId
  )

  fun login(
    method: String
  ): DirectEvent = FirebaseLoginEvent(method)

  fun signUp(
    method: String
  ): DirectEvent = FirebaseSignUpEvent(method)
}