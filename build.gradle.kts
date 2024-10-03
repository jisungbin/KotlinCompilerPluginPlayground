plugins {
  id("org.jetbrains.kotlin.jvm") version "2.0.21-RC"
  id("com.vanniktech.maven.publish") version "0.29.0"
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
  implementation(kotlin("compiler", version = "2.0.21-RC"))
  testImplementation(kotlin("test-junit5"))
}
