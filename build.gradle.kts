plugins {
  id("org.jetbrains.kotlin.jvm") version "2.1.10"
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
  // kotlinCompilerPluginClasspath(kotlin("compose-compiler-plugin-embeddable", version = "2.1.10"))

  implementation(kotlin("compiler-embeddable", version = "2.1.10"))
  // implementation(kotlin("compose-compiler-plugin-embeddable", version = "2.1.10"))

  testImplementation(kotlin("test-junit5"))
  testImplementation("dev.zacsweers.kctfork:core:0.7.0")
}
