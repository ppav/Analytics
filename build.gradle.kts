import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
  id("com.android.application") version "7.1.1" apply false
  id("com.android.library") version "7.1.1" apply false
  id("org.jetbrains.kotlin.android") version "1.6.10" apply false
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0" apply false
}

allprojects {
  group = "io.github.ppav.analytics"
  version = "0.0.2"

  val localProps = gradleLocalProperties(rootDir)
  val emptyJavadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
  }

  afterEvaluate {
    extensions.findByType<PublishingExtension>()
        ?.apply {
          publications.withType<MavenPublication>()
              .configureEach {
                artifact(emptyJavadocJar.get())

                pom {
                  name.set("Analytics")
                  description.set("Analytics library")
                  url.set("https://github.com/ppav/analytics")

                  licenses {
                    license {
                      name.set("MIT")
                      url.set("https://opensource.org/licenses/MIT")
                    }
                  }
                  developers {
                    developer {
                      id.set(localProps.getProperty("developerId"))
                      name.set(localProps.getProperty("developerName"))
                      email.set(localProps.getProperty("developerEmail"))
                    }
                  }
                  scm {
                    url.set("https://github.com/ppav/analytics")
                  }
                }
              }

          repositories {
            maven {
              name = "sonatype"
              url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
              credentials {
                username = localProps.getProperty("ossrhUsername")
                password = localProps.getProperty("ossrhPassword")
              }
            }
          }
        }

    project.ext["signing.keyId"] = localProps.getProperty("signing.keyId")
    project.ext["signing.secretKeyRingFile"] = localProps.getProperty("signing.secretKeyRingFile")
    project.ext["signing.password"] = localProps.getProperty("signing.password")

    extensions.findByType<SigningExtension>()
        ?.apply {
          val publishing = extensions.findByType<PublishingExtension>() ?: return@apply
          sign(publishing.publications)
        }

    val isReleaseBuild = localProps.containsKey("signing.keyId")
    tasks.withType<Sign>()
        .configureEach {
          onlyIf { isReleaseBuild }
        }
  }
}

