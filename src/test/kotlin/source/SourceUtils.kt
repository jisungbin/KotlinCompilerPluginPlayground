package source

import com.tschuchort.compiletesting.SourceFile
import java.io.File

fun source(filename: String): SourceFile =
  SourceFile.kotlin("test.kt", sourceString(filename))

fun sourceString(filename: String): String =
  File("src/test/kotlin/source/$filename").readText()
