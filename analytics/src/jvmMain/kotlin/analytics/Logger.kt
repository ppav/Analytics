package analytics

import java.util.logging.Level
import java.util.logging.Logger

internal actual class Logger {

  private val logger: Logger = Logger.getLogger(LoggerName)
      .apply { level = Level.ALL }

  actual fun debug(msg: String) = logger.fine(msg)
  actual fun info(msg: String) = logger.info(msg)

  actual fun error(
    msg: String,
    error: Throwable?
  ) = logger.severe(msg
      + error?.let { e -> e.message?.let { "\n$it" } ?: "" }
      .orEmpty()
      + error?.let { e -> e.stackTrace?.let { "\n$it" } ?: "" }
      .orEmpty()
  )

}