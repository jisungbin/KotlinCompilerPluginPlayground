plugins {
  id("org.jetbrains.kotlin.jvm") version "2.1.20-RC3"
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
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

dependencies {
  kotlinCompilerPluginClasspath(kotlin("compose-compiler-plugin-embeddable", version = "2.1.20-RC3") )

  implementation(kotlin("compiler-embeddable", version = "2.1.20-RC3"))
  implementation(kotlin("compose-compiler-plugin-embeddable", version = "2.1.20-RC3"))

  implementation("org.jetbrains.compose.runtime:runtime:1.8.0-alpha04")
  implementation("org.jetbrains.compose.runtime:runtime-saveable:1.8.0-alpha04")

  testImplementation(kotlin("test-junit5"))
  testImplementation("dev.zacsweers.kctfork:core:0.7.0")
}
