import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.9.22"
  id("com.google.devtools.ksp") version "1.9.21-1.0.15"
  id("com.vanniktech.maven.publish") version "0.26.0"
}

mavenPublishing {
  @Suppress("UnstableApiUsage")
  coordinates("land.sungbin.kotlin", "kotlin-compiler-ir-dump", "0.2.1")
}

repositories {
  mavenCentral()
  gradlePluginPortal()
  google()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
  }
}

kotlin {
  compilerOptions {
    optIn.add("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
    optIn.add("org.jetbrains.kotlin.utils.addToStdlib.UnsafeCastFunction")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

dependencies {
  compileOnly(embeddedKotlin("compiler-embeddable"))
  testImplementation("dev.zacsweers.kctfork:core:0.4.0")

  implementation("com.google.auto.service:auto-service-annotations:1.1.1")
  ksp("dev.zacsweers.autoservice:auto-service-ksp:1.1.0")

  testImplementation("junit:junit:4.13.2")
  testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.10.1")
}
