plugins {
  id("org.jetbrains.kotlin.jvm") version "2.0.20-saturn-411"
  id("com.vanniktech.maven.publish") version "0.28.0"
}

mavenPublishing {
  coordinates("land.sungbin.kotlin", "kotlin-compiler-ir-dump", "2.0.0")
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
  implementation("org.jetbrains.kotlin:kotlin-compiler:2.0.20-saturn-411")
  testImplementation(kotlin("test-junit5"))
}
