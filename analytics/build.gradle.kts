import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  kotlin("multiplatform")
  kotlin("native.cocoapods")
  id("maven-publish")
  id("signing")
}

kotlin {

  jvm()

  val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
    if (System.getenv("SDK_NAME")
            ?.startsWith("iphoneos") == true
    )
      ::iosArm64
    else
      ::iosX64

  iosTarget("ios") {}

  cocoapods {
    summary = "Some description for the Shared Module"
    homepage = "Link to the Shared Module homepage"
    ios.deploymentTarget = "14.1"
    frameworkName = "analytics"
    podfile = project.file("../sample-ios/Podfile")
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(Deps.Coroutines.core)
      }
    }

    val iosMain by getting
    val iosTest by getting
    val jvmMain by getting

    val jvmTest by getting {
      dependencies {
        implementation(Deps.Coroutines.test)
        implementation(Deps.mockk)
        implementation(Deps.junit)
      }
    }
  }
}

publishing {
  publications {
    withType<MavenPublication> {
      artifactId = "analytics"
    }
  }
}