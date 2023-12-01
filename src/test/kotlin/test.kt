@file:OptIn(ExperimentalCompilerApi::class)
@file:Suppress("unused")

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import utils.source
import java.io.File

class Test {
  @get:Rule
  val tempDir = TemporaryFolder.builder().assureDeletion().build()!!

  @Test
  fun debug() {
    compile(source("test.kt"))
  }

  private fun compile(vararg sourceFiles: SourceFile) =
    prepareCompilation(*sourceFiles).compile()

  private fun prepareCompilation(vararg sourceFiles: SourceFile) =
    KotlinCompilation().apply {
      workingDir = tempDir.root
      sources = sourceFiles.asList()
      jvmTarget = JvmTarget.JVM_17.toString()
      inheritClassPath = true
      supportsK2 = false
      useK2 = false
      pluginOptions = emptyList()
      compilerPluginRegistrars = listOf(MainRegistrar())
      commandLineProcessors = emptyList()
    }
}