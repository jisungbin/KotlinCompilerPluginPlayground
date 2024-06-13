package compiler

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector

object SystemMessageCollector : MessageCollector {
  override fun clear() {}

  override fun report(
    severity: CompilerMessageSeverity,
    message: String,
    location: CompilerMessageSourceLocation?,
  ) {
    println("[${severity.name}] $message")
  }

  override fun hasErrors() = false
}
