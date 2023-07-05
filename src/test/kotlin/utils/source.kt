@file:Suppress("NOTHING_TO_INLINE")

package utils

import com.tschuchort.compiletesting.SourceFile
import java.io.File

inline fun source(filename: String) =
  SourceFile.fromPath(File("src/test/kotlin/source/$filename"))