plugins {
  id("com.android.application")
  kotlin("android")
}

dependencies {
  implementation(project(":analytics"))
  implementation(project(":analytics-consumer:yandexmetrika:"))
  implementation(project(":analytics-consumer:firebase:"))
  implementation(project(":analytics-consumer:firebase:"))

  implementation(Deps.Amplitude.sdk)
  implementation(Deps.YandexMetrica.lib)
  implementation(Deps.Firebase.core)
  implementation(Deps.Coroutines.core)
  implementation(Deps.Coroutines.android)

  implementation("androidx.appcompat:appcompat:1.4.2")

  implementation("androidx.compose.ui:ui:1.1.1")
  implementation("androidx.compose.material:material:1.1.1")
  implementation("androidx.compose.ui:ui-tooling:1.1.1")

}

android {
  compileSdk = 31
  defaultConfig {
    applicationId = "com.sample.analytics"
    minSdk = 21
    targetSdk = 31
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.1.1"
  }

}

