import androidx.compose.compiler.plugins.kotlin.lower.dumpSrc
import org.jetbrains.kotlin.backend.common.IrValidatorConfig
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.validateIr
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.config.IrVerificationMode
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment

class IrTestExtension(private val logger: Logger) : IrGenerationExtension {
  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    validateIr(
      object : MessageCollector {
        override fun clear() {}

        override fun report(severity: CompilerMessageSeverity, message: String, location: CompilerMessageSourceLocation?) {
          if (severity.isError) error(message)
        }

        override fun hasErrors(): Boolean = false
      },
      IrVerificationMode.ERROR,
    ) {
      performBasicIrValidation(
        moduleFragment,
        pluginContext.irBuiltIns,
        "TEST TEST TEST",
        IrValidatorConfig(
          checkTreeConsistency = true,
          checkTypes = false, // TODO KT-68663
          checkProperties = true,
          checkValueScopes = true,
          checkTypeParameterScopes = true,
          checkCrossFileFieldUsage = true,
          checkAllKotlinFieldsArePrivate = true,
          checkVisibilities = true,
        ),
      )
    }

    logger(moduleFragment.dumpSrc(useFir = true))
//    moduleFragment.acceptChildrenVoid(
//      object : IrVisitorVoid() {
//        override fun visitElement(element: IrElement) {
//          element.acceptChildrenVoid(this)
//        }
//
//        override fun visitCall(expression: IrCall) {
//          if (expression.symbol.owner.name.asString() == "myCall") {
//            println()
//          }
//          super.visitCall(expression)
//        }
//      }
//    )
  }
}
