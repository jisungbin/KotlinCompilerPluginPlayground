import androidx.compose.compiler.plugins.kotlin.lower.dumpSrc
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrConstructor
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrField
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrProperty
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

        override fun visitField(declaration: IrField) {
          super.visitField(declaration)
          println("field ${declaration.name}")
        }

        override fun visitProperty(declaration: IrProperty) {
          super.visitProperty(declaration)
          println("property ${declaration.name}")
        }

        override fun visitConstructor(declaration: IrConstructor) {
          super.visitConstructor(declaration)
        }
      }
    )
  }
}
