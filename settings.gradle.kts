pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}
rootProject.name = "Analytics"

include(":analytics")
include(":analytics-consumer:yandexmetrika")
include(":analytics-consumer:firebase")
include(":analytics-consumer:amplitude")

include(":sample-android")
