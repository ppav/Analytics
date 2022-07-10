package analytics.event

class EventParamsBuilder {
  private val params: MutableMap<String, Any> = mutableMapOf()

  fun withParam(
    paramName: String,
    paramValue: Any?,
    ignoreIfNull: Boolean = true
  ): EventParamsBuilder =
    withNullableParam(paramName, paramValue, ignoreIfNull)

  fun withParam(
    value: Pair<String, Any?>,
    ignoreIfNull: Boolean = true
  ) = withNullableParam(value.first, value.second, ignoreIfNull)

  private fun withNullableParam(
    key: String,
    value: Any?,
    ignoreIfNull: Boolean
  ): EventParamsBuilder {
    when (ignoreIfNull) {
      true -> if (value != null) params[key] = value
      false -> params[key] = value.toString()
    }
    return this
  }

  fun build() = params
}