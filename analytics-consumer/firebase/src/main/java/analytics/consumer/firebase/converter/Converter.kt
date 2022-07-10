package analytics.consumer.firebase.converter

import analytics.event.Sum
import android.os.Bundle
import android.os.Parcelable
import com.google.firebase.analytics.FirebaseAnalytics

internal fun Sum.toParams(): Map<String, Any> {
  return mapOf(
    FirebaseAnalytics.Param.VALUE to amount,
    FirebaseAnalytics.Param.CURRENCY to currency
  )
}

internal fun toBundle(): (Map<String, Any>) -> Bundle = {
  it.keys
    .fold(Bundle()) { bundle, key ->
      bundle.apply {
        it.getValue(key)
          .let { value ->
            when (value) {
              is Double -> putDouble(key, value)
              is Int -> putInt(key, value)
              is Float -> putFloat(key, value)
              is Boolean -> putBoolean(key, value)
              is Parcelable -> putParcelable(key, value)
              else -> putString(key, value.toString())

            }
          }
      }
    }
}