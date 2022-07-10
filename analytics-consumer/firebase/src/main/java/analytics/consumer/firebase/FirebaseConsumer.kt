package analytics.consumer.firebase

import analytics.BaseAnalyticsConsumer
import analytics.Events
import analytics.consumer.firebase.converter.toBundle
import analytics.consumer.firebase.converter.toParams
import analytics.consumer.firebase.event.FirebaseLoginEvent
import analytics.consumer.firebase.event.FirebasePurchaseEvent
import analytics.consumer.firebase.event.FirebaseRefundEvent
import analytics.consumer.firebase.event.FirebaseSignUpEvent
import analytics.event.DirectEvent
import analytics.event.Event
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseConsumer : BaseAnalyticsConsumer {

  constructor(
    firebaseAnalytics: FirebaseAnalytics,
    events: Events
  ) : super(events) {
    client = firebaseAnalytics
  }

  constructor(
    firebaseAnalytics: FirebaseAnalytics,
    events: Collection<String>
  ) : super(events + FirebaseDirectEvent.EVENTS) {
    client = firebaseAnalytics
  }

  private val client: FirebaseAnalytics

  override fun acceptDefaultEvent(event: Event) =
    client.logEvent(
        event.event,
        event.params
            .plus(event.sum?.toParams() ?: emptyMap())
            .let(toBundle())
    )

  override fun acceptDirectEvent(event: DirectEvent) {
    when (event) {
      is FirebasePurchaseEvent -> acceptPurchaseEvent(event)
      is FirebaseRefundEvent -> acceptRefundEvent(event)
      is FirebaseLoginEvent -> acceptLoginEvent(event)
      is FirebaseSignUpEvent -> acceptSignUpEvent(event)
    }
  }

  private fun acceptPurchaseEvent(event: FirebasePurchaseEvent) {
    client.logEvent(
        FirebaseAnalytics.Event.PURCHASE, event.toMap()
        .let(toBundle())
    )
  }

  private fun acceptRefundEvent(event: FirebaseRefundEvent) {
    client.logEvent(
        FirebaseAnalytics.Event.REFUND, event.toMap()
        .let(toBundle())
    )
  }

  private fun acceptLoginEvent(event: FirebaseLoginEvent) {
    client.logEvent(
        FirebaseAnalytics.Event.LOGIN, event.toMap()
        .let(toBundle())
    )
  }

  private fun acceptSignUpEvent(event: FirebaseSignUpEvent) {
    client.logEvent(
        FirebaseAnalytics.Event.SIGN_UP, event.toMap()
        .let(toBundle())
    )
  }
}