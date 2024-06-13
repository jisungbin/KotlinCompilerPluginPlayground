package source

import compiler.SourceFile
import java.io.File

fun source(filename: String): SourceFile =
  SourceFile(name = filename, source = sourceString(filename))

fun sourceString(filename: String): String =
  File("src/test/kotlin/source/$filename").readText()
