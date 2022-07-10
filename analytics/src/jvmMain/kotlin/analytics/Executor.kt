package analytics

import kotlinx.coroutines.Dispatchers

actual class  Executor {

  actual fun dispatcher() = Dispatchers.IO

}