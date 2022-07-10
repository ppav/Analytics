package analytics.event

sealed class AnalyticsEvent(open val event: String)
open class DirectEvent(event: String) : AnalyticsEvent(event)
data class Event(
  override val event: String,
  val sum: Sum?,
  val params: Map<String, Any>
) : AnalyticsEvent(event) {

  companion object
  class Builder(
    private val name: String,
  ) {
    private val paramsBuilder = EventParamsBuilder()
    private var purchaseSum: Sum? = null

    fun withParam(
      paramName: String,
      paramValue: Any?
    ) = withParam(paramName, paramValue, true)

    fun withParam(
      paramName: String,
      paramValue: Any?,
      ignoreIfNull: Boolean = true
    ) = this.also { paramsBuilder.withParam(paramName, paramValue, ignoreIfNull) }

    fun withParam(
      value: Pair<String, Any?>,
      ignoreIfNull: Boolean = true
    ) = this.also { paramsBuilder.withParam(value, ignoreIfNull) }

    fun withSum(
      amount: Double,
      currency: String
    ) = this.also {
      purchaseSum = Sum(amount, currency)
      return this
    }

    fun build() =
      Event(name, purchaseSum, paramsBuilder.build())
  }
}


