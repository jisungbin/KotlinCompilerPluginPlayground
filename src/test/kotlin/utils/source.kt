package utils

import com.tschuchort.compiletesting.SourceFile
import java.io.File

@Suppress("NOTHING_TO_INLINE")
inline fun source(filename: String): SourceFile =
  SourceFile.fromPath(File("src/test/kotlin/source/$filename"))
