package compiler

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.config.CompilerConfiguration

class SystemLogger(private val configuration: CompilerConfiguration) {
  operator fun invoke(message: Any?, location: CompilerMessageSourceLocation? = null) {
    println(message.toString())
  }
}