@file:Suppress("unused")

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.config.CompilerConfiguration

interface Logger {
  val tag: String

  fun warn(message: Any?, location: CompilerMessageSourceLocation? = null)
  fun error(message: Any?, location: CompilerMessageSourceLocation? = null)
  fun throwError(message: Any?, location: CompilerMessageSourceLocation? = null): Nothing

  operator fun invoke(value: Any?, location: CompilerMessageSourceLocation? = null) {
    warn(message = value, location = location)
  }

  fun Any?.prependLogPrefix(withNewline: Boolean = false) =
    "[$tag] ${if (withNewline) "\n$this" else " $this"}"
}

fun CompilerConfiguration.getLogger(tag: String): Logger {
  val messageCollector = get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)

  return object : Logger {
    override val tag = tag

    override fun warn(message: Any?, location: CompilerMessageSourceLocation?) {
      messageCollector.report(CompilerMessageSeverity.WARNING, message.toString(), location)
    }

    override fun error(message: Any?, location: CompilerMessageSourceLocation?) {
      messageCollector.report(CompilerMessageSeverity.ERROR, message.toString(), location)
    }

    override fun throwError(message: Any?, location: CompilerMessageSourceLocation?): Nothing {
      error(message, location)
      kotlin.error(message.toString())
    }
  }
}