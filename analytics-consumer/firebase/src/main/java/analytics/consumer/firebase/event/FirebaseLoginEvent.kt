package analytics.consumer.firebase.event

import analytics.consumer.firebase.FirebaseDirectEvent
import analytics.event.DirectEvent
import analytics.event.EventParamsBuilder
import com.google.firebase.analytics.FirebaseAnalytics

internal data class FirebaseLoginEvent(
  val method: String
) : DirectEvent(FirebaseDirectEvent.LOGIN) {

  internal fun toMap(): Map<String, Any> = EventParamsBuilder()
      .withParam(FirebaseAnalytics.Param.METHOD, method)
      .build()
}

