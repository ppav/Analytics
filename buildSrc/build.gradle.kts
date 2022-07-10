plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal() // To use 'maven-publish' and 'signing' plugins in our own plugin
}

kotlin {
    val main by sourceSets.getting {
        kotlin.srcDir("src/main/kotlin")
    }
}



