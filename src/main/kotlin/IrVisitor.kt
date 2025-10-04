import androidx.compose.compiler.plugins.kotlin.hasComposableAnnotation
import androidx.compose.compiler.plugins.kotlin.lower.dumpSrc
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrReturn
import org.jetbrains.kotlin.ir.util.statements
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

        override fun visitFunction(declaration: IrFunction) {
          if (declaration.name.asString() == "<get-value>" || declaration.isComposableDelegatedAccessor()) {
            println()
          }
          super.visitFunction(declaration)
        }
      }
    )
  }
}

fun IrFunction.isComposableDelegatedAccessor(): Boolean =
  origin == IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR &&
    body?.let { body ->
      val returnStatement = body.statements.singleOrNull() as? IrReturn
      val callStatement = returnStatement?.value as? IrCall
      val target = callStatement?.symbol?.owner
      target?.hasComposableAnnotation()
    } == true
