object Sdk {
  const val compile = 31
  const val min = 21
  const val target = 21
}

object Deps {


  const val kotlin = "1.6.10"
  const val junit = "junit:junit:4.12"
  const val mockk = "io.mockk:mockk:1.10.0"
  const val okhttp = "com.squareup.okhttp3:okhttp:4.9.3"



  object Coroutines {
    private const val version = "1.6.0-native-mt"

    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"
    const val test  = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2"
  }

  object YandexMetrica {
    const val lib = "com.yandex.android:mobmetricalib:4.2.0"
  }

  object Firebase {
    const val core = "com.google.firebase:firebase-core:21.0.0"
  }

  object Amplitude {
    const val sdk = "com.amplitude:android-sdk:2.25.2"
  }

}

