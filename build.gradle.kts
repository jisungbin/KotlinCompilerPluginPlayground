plugins {
  id("org.jetbrains.kotlin.jvm") version "2.2.20"
  id("com.vanniktech.maven.publish") version "0.31.0"
}

mavenPublishing {
  coordinates("land.sungbin.kotlin", "kotlin-compiler-playground", "2.0.0")
}

kotlin {
  compilerOptions {
    optIn.addAll(
      "kotlin.RequiresOptIn",
      "org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi",
      "org.jetbrains.kotlin.utils.addToStdlib.UnsafeCastFunction",
      "org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI",
    )
    freeCompilerArgs.add("-Xcontext-parameters")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

dependencies {
  implementation(kotlin("compiler-embeddable", version = "2.2.20"))
  implementation(kotlin("compose-compiler-plugin-embeddable", version = "2.2.20"))
  implementation("androidx.compose.runtime:runtime:1.9.0")

  testImplementation(kotlin("test-junit5"))
  testImplementation("dev.zacsweers.kctfork:core:0.9.0")
}
