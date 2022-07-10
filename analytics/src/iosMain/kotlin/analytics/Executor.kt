package analytics

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual class  Executor {

  actual fun dispatcher() = Dispatchers.Default

}