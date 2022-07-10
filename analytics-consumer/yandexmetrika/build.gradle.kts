plugins {
  id("com.android.library")
  id("maven-publish")
  id("signing")
  kotlin("android")
}

android {
  compileSdk = Sdk.compile
  defaultConfig {
    minSdk = Sdk.min
    targetSdk = Sdk.target
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  api(project(":analytics"))
  implementation(Deps.YandexMetrica.lib)
}

val sourceJar by tasks.registering(Jar::class) {
  from(android.sourceSets["main"].java.srcDirs().srcDirs)
  archiveClassifier.set("sources")
}

publishing {
  publications {
    create<MavenPublication>("release") {
      artifactId = "consumer-${project.name}"
      artifact(sourceJar.get())
      artifact("$buildDir/outputs/aar/${project.name}-${name}.aar")
    }
  }
}
