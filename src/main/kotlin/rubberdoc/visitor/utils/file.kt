package rubberdoc.visitor.utils

import org.jetbrains.kotlin.backend.jvm.ir.getIoFile
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.util.file
import rubberdoc.node.signature.FileLocation
import team.duckie.quackquack.util.backend.kotlinc.locationOf
import java.io.File

fun IrDeclaration.getIOFileAndFileLocationPair(): Pair<File?, FileLocation> {
  val file = file
  val containingFile = file.getIoFile()
  val sourceLocation = file.locationOf(this)

  return containingFile to sourceLocation.asFileLocation()
}

fun CompilerMessageSourceLocation.asFileLocation() =
  FileLocation(
    startLine = line,
    startColumn = column,
    endLine = lineEnd,
    endColumn = columnEnd,
  )