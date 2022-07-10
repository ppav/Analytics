package analytics

internal const val LoggerName = "logger_analytics"
internal expect class Logger() {

  fun debug(msg: String)
  fun info(msg: String)
  fun error(
    msg: String,
    error: Throwable?
  )
}