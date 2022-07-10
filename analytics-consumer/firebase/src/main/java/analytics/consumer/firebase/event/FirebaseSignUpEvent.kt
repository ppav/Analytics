package analytics.consumer.firebase.event

import analytics.consumer.firebase.FirebaseDirectEvent
import analytics.event.DirectEvent
import analytics.event.EventParamsBuilder
import com.google.firebase.analytics.FirebaseAnalytics

internal data class FirebaseSignUpEvent(
  val method: String
) : DirectEvent(FirebaseDirectEvent.SIGN_UP) {

  internal fun toMap(): Map<String, Any> = EventParamsBuilder()
      .withParam(FirebaseAnalytics.Param.METHOD, method)
      .build()
}

