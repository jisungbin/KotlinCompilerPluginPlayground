import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.messageCollector

class Logger(private val configuration: CompilerConfiguration) {
  operator fun invoke(message: Any?, location: CompilerMessageSourceLocation? = null) {
    configuration.messageCollector
      .report(CompilerMessageSeverity.WARNING, message.toString(), location)
  }
}
