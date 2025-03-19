@file:Suppress("UnstableApiUsage")

rootProject.name = "KotlinCompilerPluginPlayground"

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
  }
}
