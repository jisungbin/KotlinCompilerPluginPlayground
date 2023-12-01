package util

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.config.CompilerConfiguration

class Logger(configuration: CompilerConfiguration) {
  private val messageCollector = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)

  operator fun invoke(message: Any?, location: CompilerMessageSourceLocation? = null) {
    messageCollector.report(CompilerMessageSeverity.WARNING, message.toString(), location)
  }
}
