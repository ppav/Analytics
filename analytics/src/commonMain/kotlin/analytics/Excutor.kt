package analytics

import kotlinx.coroutines.CoroutineDispatcher

expect class Executor() {
  fun dispatcher() : CoroutineDispatcher
}
