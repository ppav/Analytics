object Sdk {
  const val compile = 31
  const val min = 21
  const val target = 21
}

object Deps {

  const val junit = "junit:junit:4.12"
  const val mockk = "io.mockk:mockk:1.10.0"
  const val okhttp = "com.squareup.okhttp3:okhttp:4.9.3"

  object Coroutines {
    private const val version = "1.6.4"

    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
  }

  object YandexMetrica {
    /*https://mvnrepository.com/artifact/com.yandex.android/mobmetricalib*/
    const val lib = "com.yandex.android:mobmetricalib:5.3.0"
  }

  object Firebase {
    /*https://mvnrepository.com/artifact/com.google.firebase/firebase-core*/
    const val core = "com.google.firebase:firebase-core:21.1.1"
  }

  object Amplitude {
    /*https://mvnrepository.com/artifact/com.amplitude/android-sdk*/
    const val sdk = "com.amplitude:android-sdk:3.35.1"
  }

}

