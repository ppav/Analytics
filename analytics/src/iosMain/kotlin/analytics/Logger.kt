package analytics

internal actual class Logger {

  actual fun debug(msg: String) = println("💚 DEBUG $msg")
  actual fun info(msg: String) = println("💙 INFO $msg")

  actual fun error(
    msg: String,
    error: Throwable?
  ) = println("❤️ ERROR $msg"
      + error?.let { e -> e.message?.let { "\n$it" } ?: "" }
      .orEmpty()
  )

}