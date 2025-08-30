import androidx.compose.compiler.plugins.kotlin.lower.dumpSrc
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference
import org.jetbrains.kotlin.ir.visitors.IrVisitorVoid
import org.jetbrains.kotlin.ir.visitors.acceptChildrenVoid

class IrTestExtension(private val logger: Logger) : IrGenerationExtension {
  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    logger(moduleFragment.dumpSrc(useFir = true))
    moduleFragment.acceptChildrenVoid(
      object : IrVisitorVoid() {
        override fun visitElement(element: IrElement) {
          element.acceptChildrenVoid(this)
        }

        override fun visitLocalDelegatedPropertyReference(expression: IrLocalDelegatedPropertyReference) {
          super.visitLocalDelegatedPropertyReference(expression)
//          logger("LocalDelegatedPropertyReference: ${expression.dump()}")
          logger("LocalDelegatedPropertyReference: ${expression.dumpSrc(useFir = true)}")
        }
      }
    )
  }
}
